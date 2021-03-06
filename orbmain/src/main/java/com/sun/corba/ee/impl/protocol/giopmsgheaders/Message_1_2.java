/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 1997-2011 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.corba.ee.impl.protocol.giopmsgheaders;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.sun.corba.ee.spi.ior.iiop.GIOPVersion;
import com.sun.corba.ee.spi.protocol.RequestId;

import com.sun.corba.ee.spi.misc.ORBConstants;
import com.sun.corba.ee.impl.protocol.RequestIdImpl;

public class Message_1_2 extends Message_1_1
{
    protected int request_id = (int) 0;

    Message_1_2() {}
    
    Message_1_2(int _magic, GIOPVersion _GIOP_version, byte _flags,
            byte _message_type, int _message_size) {

        super(_magic,
              _GIOP_version,
              _flags,
              _message_type,
              _message_size);
    }    

    /**
     * The byteBuffer is presumed to have contents of the message already
     * read in.  It must have 12 bytes of space at the beginning for the GIOP header,
     * but the header doesn't have to be copied in.
     */
    public void unmarshalRequestID(ByteBuffer byteBuffer) {
        byteBuffer.order(isLittleEndian() ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
        request_id = byteBuffer.getInt(GIOPMessageHeaderLength);
    }

    public void write(org.omg.CORBA.portable.OutputStream ostream) {
        if (getEncodingVersion() == ORBConstants.CDR_ENC_VERSION) {
            super.write(ostream);
            return;
        }
        GIOPVersion gv = GIOP_version; // save
        GIOP_version = GIOPVersion.getInstance(GIOPVersion.V13_XX.getMajor(),
                                               getEncodingVersion());
        super.write(ostream);
        GIOP_version = gv; // restore
    }

    public RequestId getCorbaRequestId() {
        return new RequestIdImpl(this.request_id);
    }
}

