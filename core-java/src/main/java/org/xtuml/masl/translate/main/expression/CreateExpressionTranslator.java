//
// File: NameExpressionTranslator.java
//
// UK Crown Copyright (c) 2006. All Rights Reserved.
//
package org.xtuml.masl.translate.main.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xtuml.masl.cppgen.Function;
import org.xtuml.masl.cppgen.TypeUsage;
import org.xtuml.masl.metamodel.expression.CreateExpression;
import org.xtuml.masl.metamodel.expression.Expression;
import org.xtuml.masl.metamodel.object.AttributeDeclaration;
import org.xtuml.masl.metamodel.object.ObjectDeclaration;
import org.xtuml.masl.metamodel.statemodel.State;
import org.xtuml.masl.metamodel.type.TypeDefinition.ActualType;
import org.xtuml.masl.translate.main.Architecture;
import org.xtuml.masl.translate.main.Scope;
import org.xtuml.masl.translate.main.Types;
import org.xtuml.masl.translate.main.object.ObjectTranslator;



public class CreateExpressionTranslator extends ExpressionTranslator
{

  CreateExpressionTranslator ( final CreateExpression createExpression, final Scope scope )
  {
    final ObjectDeclaration object = createExpression.getObject();


    final ObjectTranslator objectTrans = ObjectTranslator.getInstance(object);
    final Function function = objectTrans.getCreateInstance();

    final Map<AttributeDeclaration, Expression> paramLookup = new HashMap<AttributeDeclaration, Expression>();
    State state = null;
    for ( final CreateExpression.AttributeValue value : createExpression.getAggregate() )
    {
      paramLookup.put(value.getAttribute(), value.getValue());
    }
    state = createExpression.getState();

    final List<org.xtuml.masl.cppgen.Expression> params = new ArrayList<org.xtuml.masl.cppgen.Expression>();
    for ( final AttributeDeclaration att : object.getAttributes() )
    {
      if ( att.isIdentifier() || !att.isReferential() )
      {
        if ( att.isUnique() )
        {
          params.add(objectTrans.getGetUniqueId(att).asFunctionCall());
        }
        else if ( att.getType().getBasicType().getActualType() == ActualType.TIMER )
        {
          params.add(Architecture.Timer.createTimer);
        }
        else
        {
          Expression maslValue = paramLookup.get(att);

          if ( maslValue == null )
          {
            maslValue = att.getDefault();
          }

          if ( maslValue == null )
          {
            final TypeUsage type = Types.getInstance().getType(att.getType());
            params.add(type.getDefaultValue());
          }
          else
          {
            params.add(createTranslator(maslValue, scope, att.getType()).getReadExpression());
          }
        }
      }
    }
    if ( state != null )
    {
      params.add(objectTrans.getNormalFsm().getState(state));
    }

    setReadExpression(function.asFunctionCall(params));
    setWriteableExpression(function.asFunctionCall(params));

  }


}
