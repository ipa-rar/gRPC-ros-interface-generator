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
 
package org.acumos.gen.ros.ros1

import com.google.protobuf.DescriptorProtos.FileDescriptorProto

import static extension org.acumos.gen.ros.TrafoUtils.*

/**
 * Create build files for ROS1, i.e. CMakeLists.txt and package.xml
 */
class CreateBuildFiles {
	/**
	 * Create contents for a proto message type
	 */
	static def createCMakeLists(FileDescriptorProto proto) '''
		cmake_minimum_required(VERSION 3.0.2)
		project(«proto.name.stripExt»_msgs)
		
		find_package(catkin REQUIRED COMPONENTS
		  message_generation
		  roscpp
		  rospy
		  std_msgs
		)
		## Generate messages in the 'msg' folder
		add_message_files(
		   FILES
			«FOR msgType : proto.messageTypeList»
				"msg/«msgType.name».msg"
			«ENDFOR»
		 )
		
		## Generate added messages and services with any dependencies listed here
		 generate_messages(
		   DEPENDENCIES
		   std_msgs
		 )
		
		## CATKIN_DEPENDS: catkin_packages dependent projects also need
		catkin_package(
		  CATKIN_DEPENDS
		  message_generation
		  roscpp
		  rospy
		  std_msgs
		)
		
		include_directories(
		  ${catkin_INCLUDE_DIRS}
		)
	'''

	static def createPackageXML(FileDescriptorProto proto) '''
		<?xml version="1.0"?>
		<package format="2">
			<name>«proto.name.stripExt»_msgs</name>
			<version>0.0.0</version>
			<description>generated from proto "«proto.name»" in package "«proto.package»"</description>
			<maintainer email="ragesh.ramachandran@ipa.fraunhofer.de">TODO ragesh_ramachandran</maintainer>
			<license>TODO: License declaration</license>

			<buildtool_depend>catkin</buildtool_depend>
			<build_depend>message_generation</build_depend>
			<build_depend>roscpp</build_depend>
			<build_depend>rospy</build_depend>
			<build_depend>std_msgs</build_depend>
			<build_export_depend>roscpp</build_export_depend>
			<build_export_depend>rospy</build_export_depend>
			<build_export_depend>std_msgs</build_export_depend>
			<exec_depend>roscpp</exec_depend>
			<exec_depend>rospy</exec_depend>
			<exec_depend>std_msgs</exec_depend>
			<exec_depend>message_generation</exec_depend>
			buildtool_depend>ament_cmake</buildtool_depend>
		</package>
	'''

}
