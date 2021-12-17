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

package org.acumos.gen.ros;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.acumos.gen.ros.ros1.CreateROS1Files;
import org.acumos.gen.ros.ros2.CreateROS2Files;

import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Parse a proto descriptor set
 */
public class ParseProto {

	private static final String ROS1 = "ros1"; //$NON-NLS-1$
	private static final String ROS2 = "ros2"; //$NON-NLS-1$

	/**
	 * Return a proto file descriptor from a compiled file (descriptor set)
	 * 
	 * @param dsFileName
	 *            a descriptor-set file (create e.g. with "protoc --descriptor_set_out")
	 * @return the obtained file descriptor
	 *
	 * @throws InvalidProtocolBufferException
	 * @throws Descriptors.DescriptorValidationException
	 * @throws FileNotFoundException 
	 */
	public static FileDescriptorProto getDescriptor(String dsFileName) throws InvalidProtocolBufferException, Descriptors.DescriptorValidationException, FileNotFoundException {
		InputStream is = new FileInputStream(dsFileName);
		FileDescriptorProto proto = null;
		try {
			byte data[] = is.readAllBytes();
			FileDescriptorSet protoSet = FileDescriptorSet.parseFrom(data);
			// DescriptorProtos.FileDescriptorProto descriptorProto = DescriptorProtos.FileDescriptorProto.parseFrom(x);
			proto = protoSet.getFileList().iterator().next();
			// proto = Descriptors.FileDescriptor.buildFrom(descriptorProto, new Descriptors.FileDescriptor[0]);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return proto;
	}

	protected static void printUsage() {
		System.err.println(String.format("usage: ParseProto %s|%s <path to descriptor set file>", ROS1, ROS2)); //$NON-NLS-1$
	}

	public static void main(String args[]) {
		try {
			if (args.length < 2) {
				printUsage();
				return;
			}
			String rosVersion = args[0];
			if (!rosVersion.equals(ROS1) && !rosVersion.equals(ROS2)) {
				printUsage();
				return;
			}
			FileDescriptorProto proto = getDescriptor(args[1]);
			FileWriter fw = new FileWriter(TrafoUtils.stripExt(proto.getName())); 
			if (rosVersion.equals(ROS1)) {
				CreateROS1Files.createFiles(fw, proto);
			}
			else {
				CreateROS2Files.createFiles(fw, proto);
			}
		} catch (InvalidProtocolBufferException | DescriptorValidationException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
