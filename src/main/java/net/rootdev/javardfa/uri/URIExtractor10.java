/*
 * (c) Copyright 2010 University of Bristol
 * All rights reserved.
 * [See end of file]
 */
package net.rootdev.javardfa.uri;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import net.rootdev.javardfa.Constants;
import net.rootdev.javardfa.EvalContext;
import net.rootdev.javardfa.Resolver;
import net.rootdev.javardfa.Setting;

/**
 * This uses the RDFa 1.0 URI logic
 *
 * @author pldms
 */
public class URIExtractor10 implements URIExtractor {
    private Set<Setting> settings;
    private final Resolver resolver;

    public URIExtractor10(Resolver resolver) {
        this.resolver = resolver;
    }

    public void setSettings(Set<Setting> settings) {
        this.settings = settings;
    }
    
    public String getURI(StartElement element, QName attrName, EvalContext context) {
        Attribute attr = element.getAttributeByName(attrName);
        if (attr == null) return null;
        
        if (attrName.equals(Constants.href) || attrName.equals(Constants.src)) // A URI
        {
            if (attr.getValue().length() == 0) {
                return context.getBase();
            } else {
                return resolver.resolve(context.getBase(), attr.getValue());
            }
        }
        if (attrName.equals(Constants.about) || attrName.equals(Constants.resource)) // Safe CURIE or URI
        {
            return expandSafeCURIE(element, attr.getValue(), context);
        }
        if (attrName.equals(Constants.datatype)) // A CURIE
        {
            String val = attr.getValue();
            if (val.length() == 0) {
                return "";
            }
            else {
                return expandCURIE(element, attr.getValue(), context);
            }
        }
        throw new RuntimeException("Unexpected attribute: " + attr);
    }
    
    public List<String> getURIs(StartElement element, QName attrName, EvalContext context) {
        Attribute attr = element.getAttributeByName(attrName);
        if (attr == null) return null;
        
        List<String> uris = new LinkedList<String>();
        String[] curies = attr.getValue().split("\\s+");
        boolean permitReserved = Constants.rel.equals(attr.getName()) ||
                Constants.rev.equals(attr.getName());
        for (String curie : curies) {
            if (Constants.SpecialRels.contains(curie.toLowerCase())) {
                if (permitReserved)
                    uris.add("http://www.w3.org/1999/xhtml/vocab#" + curie.toLowerCase());
            } else {
                String uri = expandCURIE(element, curie, context);
                if (uri != null) {
                    uris.add(uri);
                }
            }
        }
        return uris;
    }

    public String expandCURIE(StartElement element, String value, EvalContext context) {
        if (value.startsWith("_:")) {
            if (!settings.contains(Setting.ManualNamespaces)) return value;
            if (element.getNamespaceURI("_") == null) return value;
        }
        if (settings.contains(Setting.FormMode) && // variable
                value.startsWith("?")) {
            return value;
        }
        int offset = value.indexOf(":") + 1;
        if (offset == 0) {
            //throw new RuntimeException("Is this a curie? \"" + value + "\"");
            return null;
        }
        String prefix = value.substring(0, offset - 1);

        // Apparently these are not allowed to expand
        if ("xml".equals(prefix) || "xmlns".equals(prefix)) return null;

        String namespaceURI = prefix.length() == 0 ? "http://www.w3.org/1999/xhtml/vocab#" : element.getNamespaceURI(prefix);
        if (namespaceURI == null) {
            return null;
            //throw new RuntimeException("Unknown prefix: " + prefix);
        }
        
        return namespaceURI + value.substring(offset);
    }

    public String expandSafeCURIE(StartElement element, String value, EvalContext context) {
        if (value.startsWith("[") && value.endsWith("]")) {
            return expandCURIE(element, value.substring(1, value.length() - 1), context);
        } else {
            if (value.length() == 0) {
                return context.getBase();
            }

            if (settings.contains(Setting.FormMode) &&
                    value.startsWith("?")) {
                return value;
            }

            return resolver.resolve(context.getBase(), value);
        }
    }

    public String resolveURI(String uri, EvalContext context) {
        return resolver.resolve(context.getBase(), uri);
    }

}

/*
 * (c) Copyright 2010 University of Bristol
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
