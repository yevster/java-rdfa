/*
 * (c) Copyright 2009 University of Bristol
 * All rights reserved.
 * [See end of file]
 */
package net.rootdev.javardfa;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;

/**
 *
 * @author pldms
 */
public class Constants {
    final static List<String> _allowed = Arrays.asList(
            "alternate", "appendix", "bookmark", "cite",
            "chapter", "contents", "copyright", "first",
            "glossary", "help", "icon", "index", "last",
            "license", "meta", "next", "p3pv1", "prev",
            "collection", "role", "section", "stylesheet",
            "subsection", "start", "top", "up");
    final static Set<String> SpecialRels = new HashSet<String>(_allowed);
    // Suggestion: switch this for object produced by factory that matches QNames
    // we can then en-slacken if needed by passing in different factory etc
    final static QName about = new QName("about"); // safe
    final static QName resource = new QName("resource"); // safe
    final static QName href = new QName("href"); // URI
    final static QName src = new QName("src"); // URI
    final static QName property = new QName("property"); // CURIE
    final static QName datatype = new QName("datatype"); // CURIE
    final static QName typeof = new QName("typeof"); // CURIE
    final static QName rel = new QName("rel"); // Link types and CURIES
    final static QName rev = new QName("rev"); // Link type and CURIES
    final static QName content = new QName("content");
    final static QName xmllang = new QName("xml:lang");
    final static QName xmllangNS = new QName("http://www.w3.org/XML/1998/namespace", "lang", "xml");
    final static QName lang = new QName("lang");
    final static QName base = new QName("http://www.w3.org/1999/xhtml", "base");
    final static QName head = new QName("http://www.w3.org/1999/xhtml", "head");
    final static QName body = new QName("http://www.w3.org/1999/xhtml", "body");
    // Hack bits
    final static QName input = new QName("http://www.w3.org/1999/xhtml", "input");
    final static QName name = new QName("name");
    final static QName form = new QName("http://www.w3.org/1999/xhtml", "form");
    final static Collection<String> rdfType = Collections.singleton("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    
    // RDFa 1.1
    final static QName vocab = new QName("vocab");
    final static QName profile = new QName("profile");
}

/*
 * (c) Copyright 2009 University of Bristol
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