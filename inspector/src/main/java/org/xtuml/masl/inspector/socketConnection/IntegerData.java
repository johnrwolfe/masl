// 
// Filename : IntegerData.java
//
// UK Crown Copyright (c) 2005. All Rights Reserved
//
package org.xtuml.masl.inspector.socketConnection;

import java.io.IOException;

import org.xtuml.masl.inspector.socketConnection.ipc.CommunicationChannel;
import org.xtuml.masl.inspector.socketConnection.ipc.ReadableObject;
import org.xtuml.masl.inspector.socketConnection.ipc.WriteableObject;


public class IntegerData extends org.xtuml.masl.inspector.processInterface.IntegerData
    implements ReadableObject, WriteableObject
{

  public void read ( final CommunicationChannel channel ) throws IOException
  {
    setValue(new Integer(channel.readInt()));
  }

  public void write ( final CommunicationChannel channel ) throws IOException
  {
    channel.writeData(getValue());
  }

}
