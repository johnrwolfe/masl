//
// File: SetType.java
//
// UK Crown Copyright (c) 2008. All Rights Reserved.
//
package org.xtuml.masl.metamodel.type;

public interface DictionaryType
    extends BasicType
{

  BasicType getKeyType ();

  BasicType getValueType ();
}