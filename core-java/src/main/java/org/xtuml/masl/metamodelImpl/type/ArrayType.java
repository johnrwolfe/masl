//
// UK Crown Copyright (c) 2016. All Rights Reserved.
//
package org.xtuml.masl.metamodelImpl.type;

import org.xtuml.masl.metamodel.ASTNodeVisitor;
import org.xtuml.masl.metamodelImpl.common.Position;
import org.xtuml.masl.metamodelImpl.error.SemanticError;
import org.xtuml.masl.metamodelImpl.error.SemanticErrorCode;
import org.xtuml.masl.metamodelImpl.expression.CharacteristicExpression;
import org.xtuml.masl.metamodelImpl.expression.CharacteristicRange;
import org.xtuml.masl.metamodelImpl.expression.Expression;
import org.xtuml.masl.metamodelImpl.expression.RangeExpression;
import org.xtuml.masl.utils.HashCode;


public final class ArrayType extends CollectionType
    implements org.xtuml.masl.metamodel.type.ArrayType
{

  public static ArrayType create ( final Position position,
                                   final BasicType containedType,
                                   final Expression range,
                                   final boolean anonymous )
  {
    if ( containedType == null || range == null )
    {
      return null;
    }

    try
    {
      return new ArrayType(position, containedType, range, anonymous);
    }
    catch ( final SemanticError e )
    {
      e.report();
      return null;
    }
  }

  private ArrayType ( final Position position, final BasicType containedType, final Expression range, final boolean anonymous ) throws SemanticError
  {
    super(position, containedType, anonymous);

    if ( range instanceof RangeExpression )
    {
      RangeType.createAnonymous(IntegerType.createAnonymous()).checkAssignable(range);
      this.range = (RangeExpression)range;
    }
    else if ( range instanceof CharacteristicExpression && ((CharacteristicExpression)range).getCharacteristic() == CharacteristicExpression.Type.RANGE )
    {
      this.range = new CharacteristicRange((CharacteristicExpression)range);
    }
    else
    {
      throw new SemanticError(SemanticErrorCode.ArrayBoundsNotRange, range.getPosition());
    }
  }

  private ArrayType ( final BasicType containedType, final RangeExpression range )
  {
    super(null, containedType, true);
    this.range = range;
  }

  @Override
  public boolean equals ( final Object obj )
  {
    if ( this == obj )
    {
      return true;
    }
    if ( !(obj instanceof ArrayType) )
    {
      return false;
    }
    else
    {
      final ArrayType rhs = (ArrayType)obj;

      return collEquals(rhs) && range.equals(rhs.range);
    }
  }

  @Override
  public Expression getMaxValue ()
  {
    return getRange().getMax();
  }


  @Override
  public Expression getMinValue ()
  {
    return getRange().getMin();
  }

  @Override
  public RangeExpression getRange ()
  {
    return range;
  }

  @Override
  public int hashCode ()
  {
    return HashCode.makeHash(collHashCode(), range);
  }

  @Override
  public String toString ()
  {
    return (isAnonymousType() ? "anonymous " : "") + "array (" + range + ") of " + getContainedType();
  }

  private final RangeExpression range;

  @Override
  public ArrayType getBasicType ()
  {
    return new ArrayType(getContainedType().getBasicType(), range);
  }

  @Override
  public ActualType getActualType ()
  {
    return ActualType.ARRAY;
  }

  @Override
  public <R, P> R accept ( final ASTNodeVisitor<R, P> v, final P p ) throws Exception
  {
    return v.visitArrayType(this, p);
  }


}
