/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * 
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 * 
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
/*
 * COMPONENT_NAME: idl.parser
 *
 * ORIGINS: 27
 *
 * Licensed Materials - Property of IBM
 * 5639-D57 (C) COPYRIGHT International Business Machines Corp. 1997, 1999
 * RMI-IIOP v1.0
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.sun.tools.corba.ee.idl;

/**
 *
 **/
class ParseException extends Exception
{
  /**
   * Constructor: print the supplied message to Standard.err
   * and create a new ParseException
   * @return a new ParseException.
   **/
  ParseException (String message)
  {
    super (message);
    System.err.println (message);
    detected = true;
  } // ctor

  /**
   * Constructor: print the supplied message to Standard.err, if it
   * is not a warning, and create a new ParseException.
   * @return a new ParseException.
   **/
  ParseException (String message, boolean onlyAWarning)
  {
   super (message);
   System.err.println (message);
   if (!onlyAWarning)
     detected = true;
  }

  static ParseException abstractValueBox (com.sun.tools.corba.ee.idl.Scanner scanner)
  {
    return arg0 ("abstractValueBox", scanner);
  }

  static ParseException alreadyDeclared (com.sun.tools.corba.ee.idl.Scanner scanner, String type)
  {
    return arg1 ("alreadyDeclared", scanner, type);
  }

  static ParseException declNotInSameFile( com.sun.tools.corba.ee.idl.Scanner scanner, String type,
    String firstFile )
  {
    return arg2 ("declNotInSameFile", scanner, type, firstFile) ;
  }

  static ParseException alreadyDefaulted (com.sun.tools.corba.ee.idl.Scanner scanner)
  {
    return arg0 ("alreadydefaulted", scanner);
  }

  static ParseException alreadyDerived (com.sun.tools.corba.ee.idl.Scanner scanner, String derived, String iface)
  {
    return arg2 ("alreadyDerived", scanner, derived, iface);
  }

  static ParseException alreadyRaised (com.sun.tools.corba.ee.idl.Scanner scanner, String exception)
  {
    return arg1 ("alreadyRaised", scanner, exception);
  }

  // <d60942>
  static ParseException attributeNotType (com.sun.tools.corba.ee.idl.Scanner scanner, String attr)
  {
    return arg1 ("attributeNotType", scanner, attr);
  }

  static ParseException badAbstract (com.sun.tools.corba.ee.idl.Scanner scanner, String name)
  {
    return arg1 ("badAbstract", scanner, name);
  }

  static ParseException badCustom (com.sun.tools.corba.ee.idl.Scanner scanner)
  {
    return arg0 ("badCustom", scanner);
  }

  // <d57110>
  static ParseException badRepIDAlreadyAssigned (com.sun.tools.corba.ee.idl.Scanner scanner, String entry)
  {
    return arg1 ("badRepIDAlreadyAssigned", scanner, entry);
  }

  // <d57110>
  static ParseException badRepIDForm (com.sun.tools.corba.ee.idl.Scanner scanner, String entry)
  {
    return arg1 ("badRepIDForm", scanner, entry);
  }

  // <d56351>
  static ParseException badRepIDPrefix (com.sun.tools.corba.ee.idl.Scanner scanner, String entry, String expected, String got)
  {
    return arg3 ("badRepIDPrefix", scanner, entry, expected, got);
  }

  static ParseException badState (com.sun.tools.corba.ee.idl.Scanner scanner, String entry)
  {
    return arg1 ("badState", scanner, entry);
  }

  static ParseException branchLabel (com.sun.tools.corba.ee.idl.Scanner scanner, String label)
  {
    return arg1 ("branchLabel", scanner, label);
  }

  static ParseException branchName (com.sun.tools.corba.ee.idl.Scanner scanner, String name)
  {
    return arg1 ("branchName", scanner, name);
  }

  static ParseException duplicateInit (com.sun.tools.corba.ee.idl.Scanner scanner)
  {
    return arg0 ("duplicateInit", scanner);
  }

  static ParseException duplicateState (com.sun.tools.corba.ee.idl.Scanner scanner, String name)
  {
    return arg1 ("duplicateState", scanner, name);
  }

  static ParseException elseNoIf (com.sun.tools.corba.ee.idl.Scanner scanner)
  {
    return arg0 ("elseNoIf", scanner);
  }

  static ParseException endNoIf (com.sun.tools.corba.ee.idl.Scanner scanner)
  {
    return arg0 ("endNoIf", scanner);
  }

  static ParseException evaluationError (com.sun.tools.corba.ee.idl.Scanner scanner, String problem)
  {
    return arg1 ("evaluation", scanner, problem);
  }

  static ParseException forwardEntry (com.sun.tools.corba.ee.idl.Scanner scanner, String name)
  {
    return arg1 ("forwardEntry", scanner, name);
  }

  // <f46082.40> Cannot forward value boxes.
  static ParseException forwardedValueBox (com.sun.tools.corba.ee.idl.Scanner scanner, String name)
  {
    return arg1 ("forwardedValueBox", scanner, name);
  }

  static ParseException generic (com.sun.tools.corba.ee.idl.Scanner scanner, String message)
  {
    return arg1 ("generic", scanner, message);
  }

  static ParseException illegalArray (com.sun.tools.corba.ee.idl.Scanner scanner, String name)
  {
    return arg1 ("illegalArray", scanner, name);
  }

  static ParseException illegalException (com.sun.tools.corba.ee.idl.Scanner scanner, String name)
  {
    return arg1 ("illegalException", scanner, name);
  }

  static ParseException invalidConst (com.sun.tools.corba.ee.idl.Scanner scanner, String mustBe, String is)
  {
    return arg2 ("invalidConst1", scanner, mustBe, is);
  }

  static ParseException invalidConst (com.sun.tools.corba.ee.idl.Scanner scanner, String type)
  {
    return arg1 ("invalidConst2", scanner, type);
  }

  // <d59166> Non-escaped identifiers that collide with keywords are illegal.
  static ParseException keywordCollision (com.sun.tools.corba.ee.idl.Scanner scanner, String id)
  {
    return arg1 ("keywordCollision", scanner, id);
  }

  // <d62023> Warning for keywords that will be removed in a future version of IDL.
  static ParseException deprecatedKeywordWarning (com.sun.tools.corba.ee.idl.Scanner scanner, String id)
  {
    return arg1Warning ("deprecatedKeywordWarning", scanner, id);
  }

  // <f60858.1> Warning for above error.
  static ParseException keywordCollisionWarning (com.sun.tools.corba.ee.idl.Scanner scanner, String id)
  {
    return arg1Warning ("keywordCollisionWarning", scanner, id);
  }

  static ParseException methodClash (com.sun.tools.corba.ee.idl.Scanner scanner, String interf, String method)
  {
    return arg2 ("methodClash", scanner, interf, method);
  }

  static ParseException moduleNotType (com.sun.tools.corba.ee.idl.Scanner scanner, String module)
  {
    return arg1 ("moduleNotType", scanner, module);
  }

  // <d59067>
  static ParseException nestedValueBox (com.sun.tools.corba.ee.idl.Scanner scanner)
  {
    return arg0 ("nestedValueBox", scanner);
  }

  static ParseException noDefault (com.sun.tools.corba.ee.idl.Scanner scanner)
  {
    return arg0 ("noDefault", scanner);
  }

  static ParseException nonAbstractParent (com.sun.tools.corba.ee.idl.Scanner scanner, String baseClass, String parentClass)
  {
    return arg2 ("nonAbstractParent", scanner, baseClass, parentClass);
  }

  static ParseException nonAbstractParent2 (com.sun.tools.corba.ee.idl.Scanner scanner, String baseClass, String parentClass)
  {
    return arg2 ("nonAbstractParent2", scanner, baseClass, parentClass);
  }

  static ParseException nonAbstractParent3 (com.sun.tools.corba.ee.idl.Scanner scanner, String baseClass, String parentClass)
  {
    return arg2 ("nonAbstractParent3", scanner, baseClass, parentClass);
  }

  static ParseException notANumber (com.sun.tools.corba.ee.idl.Scanner scanner, String notNumber)
  {
    return arg1 ("notANumber", scanner, notNumber);
  }

  static ParseException nothing (String filename)
  {
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage("ParseException.nothing", filename));
  }

  static ParseException notPositiveInt (com.sun.tools.corba.ee.idl.Scanner scanner, String notPosInt)
  {
    return arg1 ("notPosInt", scanner, notPosInt);
  }

  static ParseException oneway (com.sun.tools.corba.ee.idl.Scanner scanner, String method)
  {
    return arg1 ("oneway", scanner, method);
  }

  // <d60942>
  static ParseException operationNotType (com.sun.tools.corba.ee.idl.Scanner scanner, String op)
  {
    return arg1 ("operationNotType", scanner, op);
  }

  static ParseException outOfRange (com.sun.tools.corba.ee.idl.Scanner scanner, String value, String type)
  {
    return arg2 ("outOfRange", scanner, value, type);
  }

  static ParseException recursive (com.sun.tools.corba.ee.idl.Scanner scanner, String type, String name)
  {
    return arg2 ("recursive", scanner, type, name);
  }

  static ParseException selfInherit (com.sun.tools.corba.ee.idl.Scanner scanner, String name)
  {
    return arg1 ("selfInherit", scanner, name);
  }

  static ParseException stringTooLong (com.sun.tools.corba.ee.idl.Scanner scanner, String str, String max)
  {
    return arg2 ("stringTooLong", scanner, str, max);
  }

  static ParseException syntaxError (com.sun.tools.corba.ee.idl.Scanner scanner, int expected, int got)
  {
    return arg2 ("syntax1", scanner, Token.toString(expected), Token.toString(got));
  }

  static ParseException syntaxError (com.sun.tools.corba.ee.idl.Scanner scanner, String expected, String got)
  {
    return arg2 ("syntax1", scanner, expected, got);
  }

  static ParseException syntaxError (com.sun.tools.corba.ee.idl.Scanner scanner, int[] expected, int got)
  {
    return syntaxError (scanner, expected, Token.toString(got));
  }

  static ParseException syntaxError (com.sun.tools.corba.ee.idl.Scanner scanner, int[] expected, String got)
  {
    String tokenList = "";
    for (int i = 0; i < expected.length; ++i)
      tokenList += " `" + Token.toString(expected[i]) + "'";
    return arg2 ("syntax2", scanner, tokenList, got);
  }

  static ParseException unclosedComment (String filename)
  {
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage("ParseException.unclosed", filename));
  }

  static ParseException undeclaredType (com.sun.tools.corba.ee.idl.Scanner scanner, String undeclaredType)
  {
    return arg1 ("undeclaredType", scanner, undeclaredType);
  }

  static ParseException warning (com.sun.tools.corba.ee.idl.Scanner scanner, String message)
  {
    scannerInfo (scanner);
    String[] parameters = { filename, Integer.toString (lineNumber), message, line, pointer };
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage("ParseException.warning", parameters), true);
  }

  static ParseException wrongType (com.sun.tools.corba.ee.idl.Scanner scanner, String name, String mustBe, String is)
  {
    scannerInfo (scanner);
    String[] parameters = {filename, Integer.toString (lineNumber), name, is, mustBe, line, pointer};
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage("ParseException.wrongType", parameters));
  }

  static ParseException wrongExprType (com.sun.tools.corba.ee.idl.Scanner scanner, String mustBe, String is)
  {
    scannerInfo (scanner);
    String[] parameters = {filename, Integer.toString (lineNumber), 
      is, mustBe, line, pointer};
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage("ParseException.constExprType",
            parameters));
  }

  static ParseException illegalForwardInheritance( com.sun.tools.corba.ee.idl.Scanner scanner, String declName,
    String baseName )
  {
    scannerInfo( scanner ) ;
    String[] parameters = { filename, Integer.toString(lineNumber), 
        declName, baseName, line, pointer } ;
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage(
            "ParseException.forwardInheritance", parameters) ) ;
  }

  static ParseException illegalIncompleteTypeReference( com.sun.tools.corba.ee.idl.Scanner scanner,
    String declName )
  {
    scannerInfo( scanner ) ;
    String[] parameters = { filename, Integer.toString(lineNumber), 
        declName, line, pointer } ;
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage(
            "ParseException.illegalIncompleteTypeReference", parameters) ) ;
  }

  private static void scannerInfo (com.sun.tools.corba.ee.idl.Scanner scanner)
  {
    filename   = scanner.filename ();
    line       = scanner.lastTokenLine ();
    lineNumber = scanner.lastTokenLineNumber ();
    int pos    = scanner.lastTokenLinePosition ();
    pointer    = "^";
    if (pos > 1)
    {
      byte[] bytes = new byte[ pos - 1 ];
      for (int i = 0; i < pos - 1; ++i)
        bytes[i] = (byte)' ';  // <d62023>
      pointer = new String (bytes) + pointer;
    }
  }

  private static ParseException arg0 (String msgId, com.sun.tools.corba.ee.idl.Scanner scanner)
  {
    scannerInfo (scanner);
    String[] parameters = {filename, Integer.toString (lineNumber), line, pointer};
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage("ParseException." + msgId, parameters));
  }

  private static ParseException arg1 (String msgId, com.sun.tools.corba.ee.idl.Scanner scanner, String arg1)
  {
    scannerInfo (scanner);
    String[] parameters = {filename, Integer.toString (lineNumber), arg1, line, pointer};
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage("ParseException." + msgId, parameters));
  }

  // <f60858.1>
  private static ParseException arg1Warning (String msgId, com.sun.tools.corba.ee.idl.Scanner scanner, String arg1)
  {
    scannerInfo (scanner);
    String[] parameters = {filename, Integer.toString (lineNumber), arg1, line, pointer};
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage("ParseException." + msgId, parameters), true);
  }

  private static ParseException arg2 (String msgId, com.sun.tools.corba.ee.idl.Scanner scanner, String arg1, String arg2)
  {
    scannerInfo (scanner);
    String[] parameters = {filename, Integer.toString (lineNumber), arg1, arg2, line, pointer};
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage("ParseException." + msgId, parameters));
  }

  private static ParseException arg3 (String msgId, com.sun.tools.corba.ee.idl.Scanner scanner, String arg1, String arg2, String arg3)
  {
    scannerInfo (scanner);
    String[] parameters = {filename, Integer.toString (lineNumber), arg1, arg2, arg3, line, pointer};
    return new ParseException (com.sun.tools.corba.ee.idl.Util.getMessage("ParseException." + msgId, parameters));
  }

  private static String filename  = "";
  private static String line      = "";
  private static int   lineNumber = 0;
  private static String pointer   = "^";

  static boolean detected = false;
} // class ParseException
