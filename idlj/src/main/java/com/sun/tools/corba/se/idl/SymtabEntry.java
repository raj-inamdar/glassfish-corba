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

package com.sun.tools.corba.se.idl;

// NOTES:

import java.io.PrintWriter;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

/**
 * This is the base class for all symbol table entries.
 * @see AttributeEntry
 * @see ConstEntry
 * @see EnumEntry
 * @see ExceptionEntry
 * @see IncludeEntry
 * @see InterfaceEntry
 * @see MethodEntry
 * @see ModuleEntry
 * @see ParameterEntry
 * @see PragmaEntry
 * @see PrimitiveEntry
 * @see SequenceEntry
 * @see StructEntry
 * @see TypedefEntry
 * @see UnionEntry
 **/
public class SymtabEntry
{
  public SymtabEntry ()
  {
    initDynamicVars ();
  } // ctor

  SymtabEntry (SymtabEntry that, IDLID clone)
  {
    _module     = that._module;
    _name       = that._name;
    _type       = that._type;
    _typeName   = that._typeName;
    _sourceFile = that._sourceFile;
    _info       = that._info;
    _repID      = (RepositoryID)clone.clone ();
    ((IDLID)_repID).appendToName (_name);
    if (that instanceof InterfaceEntry || that instanceof ModuleEntry || that instanceof StructEntry || that instanceof UnionEntry || (that instanceof SequenceEntry && this instanceof SequenceEntry))
      _container = that;
    else
      _container = that._container;
    initDynamicVars ();
        _comment = that._comment;       // <21jul1997daz>
  } // ctor

  /** This is a shallow copy constructor */
  SymtabEntry (SymtabEntry that)
  {
    _module     = that._module;
    _name       = that._name;
    _type       = that._type;
    _typeName   = that._typeName;
    _sourceFile = that._sourceFile;
    _info       = that._info;
    _repID      = (RepositoryID)that._repID.clone ();
    _container  = that._container;

    if (_type instanceof ForwardEntry)
      ((ForwardEntry)_type).types.addElement (this);

    initDynamicVars ();
        // <21JUL1997>
        _comment = that._comment;
  } // ctor

  void initDynamicVars ()
  {
    _dynamicVars = new Vector (maxKey + 1);
    for (int i = 0; i <= maxKey; ++i)
      _dynamicVars.addElement (null);
  } // initDynamicVars

  /** This is a shallow copy clone */
  public Object clone ()
  {
    return new SymtabEntry (this);
  } // clone

  /** @return the concatenation of the module and the name, delimited by '/'. */
  public final String fullName ()
  {
    return _module.equals ("") ? _name : _module + '/' + _name;
  } // fullName

  /** Get the name of this entry's module.  If there are modules within
      modules, each module name is separated by '/'.
      @returns this entry's module name. */
  public String module ()
  {
    return _module;
  } // module

  /** Set the module for this entry.
      @param newName the new name of the module. */
  public void module (String newName)
  {
    if (newName == null)
      _module = "";
    else
      _module = newName;
  } // module

  /** @return the name of this entry. */
  public String name ()
  {
    return _name;
  } // name

  /** Set the name.
      @param newName the new name. */
  public void name (String newName)
  {
    if (newName == null)
      _name = "";
    else
      _name = newName;

    // Update the RepositoryID
    if (_repID instanceof IDLID)
      ((IDLID)_repID).replaceName (newName);
  } // name

  /** @return the type name of this entry. */
  public String typeName ()
  {
    return _typeName;
  } // typeName

  protected void typeName (String typeName)
  {
    _typeName = typeName;
  } // typeName

  /** @return the type entry of this entry */
  public SymtabEntry type ()
  {
    return _type;
  } // type

  public void type (SymtabEntry newType)
  {
    if (newType == null)
      typeName ("");
    else
      typeName (newType.fullName ());
    _type = newType;

    if (_type instanceof ForwardEntry)
      ((ForwardEntry)_type).types.addElement (this);
  } // type

  /** The file name in which this entry was defined. */
  public IncludeEntry sourceFile ()
  {
    return _sourceFile;
  } // sourceFile

  /** The file name in which this entry was defined. */
  public void sourceFile (IncludeEntry file)
  {
    _sourceFile = file;
  } // sourceFile

  /** This must be either an InterfaceEntry or a ModuleEntry.
      It can be nothing else. */
  public SymtabEntry container ()
  {
    return _container;
  } // container

  /** This must be either an InterfaceEntry or a ModuleEntry.
      It can be nothing else. */
  public void container (SymtabEntry newContainer)
  {
    if (newContainer instanceof InterfaceEntry || newContainer instanceof ModuleEntry)
      _container = newContainer;
  } // container

  /** @return the repository ID for this entry. */
  public RepositoryID repositoryID ()
  {
    return _repID;
  } // repositoryID

  /** Set the repository ID for this entry.
      @param id the new repository ID. */
  public void repositoryID (RepositoryID id)
  {
    _repID = id;
  } // repositoryID

  /** Should this type be emitted? */
  public boolean emit ()
  {
    return _emit && _isReferencable ;
  } // emit

  public void emit (boolean emit)
  {
    _emit = emit;
  } // emit

  /* <21jul1997daz> Accessors for comment */

  public Comment comment()
  {
    return _comment;
  }

  public void comment( Comment comment )
  {
    _comment = comment;
  }

  public boolean isReferencable()
  {
    return _isReferencable ;
  }

  public void isReferencable( boolean value ) 
  {
    _isReferencable = value ;
  }

  static Stack<Boolean> includeStack = new Stack<Boolean> ();

  static void enteringInclude ()
  {
    includeStack.push (setEmit);
    setEmit = false;
  } // enteringInclude

  static void exitingInclude ()
  {
    setEmit = includeStack.pop ().booleanValue ();
  } // exitingInclude

  /** Other variables besides the default ones can be dynamically placed
      into SymTabEntry (and therefore on all symbol table entries) by
      extenders.  Before such a variable can exist, its key must be
      obtained by calling getVariableKey. */
  public static int getVariableKey ()
  {
    return ++maxKey;
  } // dynamicVariable

  /** Other variables besides the default ones can be dynamically placed
      into SymTabEntry (and therefore on all symbol table entries) by
      extenders.  This method assigns the value to the variable of the
      given key.  A valid key must be obtained by calling the method
      getVariableKey.  If the key is invalid, NoSuchFieldException is
      thrown. */
  public void dynamicVariable (int key, Object value) throws NoSuchFieldException
  {
    if (key > maxKey)
      throw new NoSuchFieldException (Integer.toString (key));
    else
    {
      if (key >= _dynamicVars.size ())
        growVars ();
      _dynamicVars.setElementAt (value, key);
    }
  } // dynamicVariable

  /** Other variables besides the default ones can be dynamically placed
      into SymTabEntry (and therefore on all symbol table entries) by
      extenders.  This method gets the value of the variable of the
      given key.  A valid key must be obtained by calling the method
      getVariableKey.  If the key is invalid, NoSuchFieldException is
      thrown. */
  public Object dynamicVariable (int key) throws NoSuchFieldException
  {
    if (key > maxKey)
      throw new NoSuchFieldException (Integer.toString (key));
    else
    {
      if (key >= _dynamicVars.size ())
        growVars ();
      return _dynamicVars.elementAt (key);
    }
  } // dynamicVariable

  void growVars ()
  {
    int diff = maxKey - _dynamicVars.size () + 1;
    for (int i = 0; i < diff; ++i)
      _dynamicVars.addElement (null);
  } // growVars

  /** Invoke a generator.  A call to this method is only meaningful
      for subclasses of SymtabEntry.  If called on this class, it
      is a no-op.
      @param symbolTable the symbol table is a hash table whose key is
       a fully qualified type name and whose value is a SymtabEntry or
       a subclass of SymtabEntry.
      @param stream the stream to which the generator should sent its output. */
  public void generate (Hashtable symbolTable, PrintWriter stream)
  {
  } // generate

  /** Access a generator.  A call to this method is only meaningful
      for subclasses of SymtabEntry.  If called on this class, it
      is a no-op.
      @return an object which implements the Generator interface. */
  public Generator generator ()
  {
    return null;
  } // generator

          static boolean setEmit   = true;
          static int   maxKey      = -1;

  private SymtabEntry  _container  = null;
  private String       _module     = "";
  private String       _name       = "";
  private String       _typeName   = "";
  private SymtabEntry  _type       = null;
  private IncludeEntry _sourceFile = null;
  private Object       _info       = null;
  private RepositoryID _repID      = new IDLID ("", "", "1.0");
  private boolean      _emit       = setEmit;
  private Comment      _comment    = null;
  private Vector       _dynamicVars;
  private boolean      _isReferencable = true ;
} // class SymtabEntry

/*=======================================================================================
  DATE<AUTHOR>   ACTION
  ---------------------------------------------------------------------------------------
  21jul1997<daz> Added _comment data member to afford transferring comments from source
                 file to target; added acessor methods for comment.
  =======================================================================================*/