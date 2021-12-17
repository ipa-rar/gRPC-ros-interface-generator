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
 
 package org.acumos.gen.ros

import com.google.protobuf.DescriptorProtos.DescriptorProto
import com.google.protobuf.DescriptorProtos.ServiceDescriptorProto

import static org.acumos.gen.ros.TrafoUtils.*

/**
 * Message creation for ROS1 or ROS2
 */
class CreateMessage {
	/**
	 * Create contents for a proto message type
	 */
	static def createMessage(DescriptorProto msgType) '''
		# fields of message type «msgType.name»
		«FOR fieldKey : msgType.allFields.keySet»
			«FOR field : getFields(msgType, fieldKey)»
««« TODO take "repeated" and "options" into account
				«TrafoUtils.rosType(field)» «field.name»
			«ENDFOR»
		«ENDFOR»
	'''

	/**
	 * Create contents for a proto service (RCP)
	 */
	static def createService(ServiceDescriptorProto serviceType) '''
		# fields of service type «serviceType.name»
		«FOR method : serviceType.methodList»
««« TODO mapping to query is likely not right
			«method.inputType.substring(1)» request
			---
			«method.outputType.substring(1)» reply
		«ENDFOR»
	'''
}