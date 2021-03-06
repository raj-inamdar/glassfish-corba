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

package com.sun.corba.ee.impl.dynamicany;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;

import com.sun.corba.ee.spi.orb.ORB ;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
import org.omg.DynamicAny.DynEnum;

public class DynEnumImpl extends DynAnyBasicImpl implements DynEnum
{
    private static final long serialVersionUID = 5049811482452048762L;
    //
    // Instance variables
    //

    // This int and the any value are kept in sync at all times
    int currentEnumeratorIndex = NO_INDEX;

    //
    // Constructors
    //

    private DynEnumImpl() {
        this(null, (Any)null, false);
    }

    // The current position of a DynEnum is always -1.
    protected DynEnumImpl(ORB orb, Any anAny, boolean copyValue) {
        super(orb, anAny, copyValue);
        index = NO_INDEX;
        // The any doesn't have to be initialized. We have a default value in this case.
        try {
            currentEnumeratorIndex = any.extract_long();
        } catch (BAD_OPERATION e) { 
            // _REVISIT_: Fix Me
            currentEnumeratorIndex = 0;
            any.type(any.type());
            any.insert_long(0);
        }
    }

    // Sets the current position to -1 and sets the value of the enumerator
    // to the first enumerator value indicated by the TypeCode.
    protected DynEnumImpl(ORB orb, TypeCode typeCode) {
        super(orb, typeCode);
        index = NO_INDEX;
        currentEnumeratorIndex = 0;
        any.insert_long(0);
    }

    //
    // Utility methods
    //

    private int memberCount() {
        int memberCount = 0;
        try {
            memberCount = any.type().member_count();
        } catch (BadKind bad) {
        }
        return memberCount;
    }

    private String memberName(int i) {
        String memberName = null;
        try {
            memberName = any.type().member_name(i);
        } catch (BadKind bad) {
        } catch (Bounds bounds) {
        }
        return memberName;
    }

    private int computeCurrentEnumeratorIndex(String value) {
        int memberCount = memberCount();
        for (int i=0; i<memberCount; i++) {
            if (memberName(i).equals(value)) {
                return i;
            }
        }
        return NO_INDEX;
    }

    //
    // DynAny interface methods
    //

    // Returns always 0 for DynEnum
    @Override
    public int component_count() {
        return 0;
    }

    // Calling current_component on a DynAny that cannot have components,
    // such as a DynEnum or an empty exception, raises TypeMismatch.
    @Override
    public org.omg.DynamicAny.DynAny current_component()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        throw new TypeMismatch();
    }

    //
    // DynEnum interface methods
    //

    // Returns the value of the DynEnum as an IDL identifier.
    public String get_as_string () {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        return memberName(currentEnumeratorIndex);
    }

    // Sets the value of the DynEnum to the enumerated value
    // whose IDL identifier is passed in the value parameter.
    // If value contains a string that is not a valid IDL identifier
    // for the corresponding enumerated type, the operation raises InvalidValue.
    public void set_as_string (String value)
        throws org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        int newIndex = computeCurrentEnumeratorIndex(value);
        if (newIndex == NO_INDEX) {
            throw new InvalidValue();
        }
        currentEnumeratorIndex = newIndex;
        any.insert_long(newIndex);
    }

    // Returns the value of the DynEnum as the enumerated values ordinal value.
    // Enumerators have ordinal values 0 to n-1,
    // as they appear from left to right in the corresponding IDL definition.
    public int get_as_ulong () {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        return currentEnumeratorIndex;
    }

    // Sets the value of the DynEnum as the enumerated values ordinal value.
    // If value contains a value that is outside the range of ordinal values
    // for the corresponding enumerated type, the operation raises InvalidValue.
    public void set_as_ulong (int value)
        throws org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (status == STATUS_DESTROYED) {
            throw wrapper.dynAnyDestroyed() ;
        }
        if (value < 0 || value >= memberCount()) {
            throw new InvalidValue();
        }
        currentEnumeratorIndex = value;
        any.insert_long(value);
    }
}
