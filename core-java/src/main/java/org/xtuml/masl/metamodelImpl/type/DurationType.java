//
// File: DurationType.java
//
// UK Crown Copyright (c) 2008. All Rights Reserved.
//
package org.xtuml.masl.metamodelImpl.type;

import org.xtuml.masl.metamodelImpl.common.Position;


public class DurationType extends BuiltinType
{

  public static DurationType create ( final Position position, final boolean anonymous )
  {
    return new DurationType(position, anonymous);
  }

  private static final DurationType ANON = new DurationType(null, true);

  public static DurationType createAnonymous ()
  {
    return ANON;
  }

  private DurationType ( final Position position, final boolean anonymous )
  {
    super(position, "duration", anonymous);
  }

  @Override
  public DurationType getPrimitiveType ()
  {
    return ANON;
  }

  @Override
  public DurationType getBasicType ()
  {
    return this;
  }

  @Override
  public ActualType getActualType ()
  {
    return ActualType.DURATION;
  }
}
