/*****************************************************************************
 * Copyright (c) 2021 CEA LIST.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 *****************************************************************************/

package org.acumos.gen.ros.ros2;

import org.acumos.gen.ros.CreateMessage;
import org.acumos.gen.ros.FileWriter;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.ServiceDescriptorProto;

public class CreateROS2Files {
	@SuppressWarnings("nls")
	public static void createFiles(FileWriter fw, FileDescriptorProto proto) {
		for (DescriptorProto msgType : proto.getMessageTypeList()) {
			fw.generateFile(String.format("%s.msg", msgType.getName()), CreateMessage.createMessage(msgType));
		}
		for (ServiceDescriptorProto svcType : proto.getServiceList()) {
			// System.err.println(CreateMessage.createService(svcType));
		}
		fw.generateFile("CMakeLists.txt", CreateBuildFiles.createCMakeLists(proto));

		fw.generateFile("package.xml", CreateBuildFiles.createPackageXML(proto));

		StringBuffer body = new StringBuffer();
		for (ServiceDescriptorProto svcType : proto.getServiceList()) {
			var pyStub = new CreatePyStub(proto, svcType);
			for (var method : svcType.getMethodList()) {
				body.append(pyStub.createStub(method));
				body.append(pyStub.createMain());
			}
		}
		fw.generateFile("client.py", body); //$NON-NLS-1$
	}
}
