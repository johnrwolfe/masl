//============================================================================//
// UK Crown Copyright (c) 2009. All rights reserved.
//
// File: RelationshipSqlRepository.cc
//
//============================================================================//
#include <iostream>
#include "sql/Exception.hh"
#include "sql/RelationshipSql.hh"
#include "sql/RelationshipSqlRepository.hh"

namespace SQL {

// ***********************************************************************
// ***********************************************************************
RelationshipSqlRepository& RelationshipSqlRepository::getInstance()
{
   static RelationshipSqlRepository instance;
   return instance;
}

// ***********************************************************************
// ***********************************************************************
RelationshipSqlRepository::RelationshipSqlRepository()
{

}

// ***********************************************************************
// ***********************************************************************
RelationshipSqlRepository::~RelationshipSqlRepository()
{

}

// ***********************************************************************
// ***********************************************************************
bool RelationshipSqlRepository::registerRelationshipSql(const RelationshipSql* const sql)
{
   const std::string relationshipKey(sql->getRelationshipName());
   repository.insert(std::make_pair(relationshipKey, sql));
   return true;
}

// ***********************************************************************
// ***********************************************************************
void RelationshipSqlRepository::deregisterRelationshipSql(const RelationshipSql* const sql)
{
    // Cannot remove the registered components as cannot be sure the
    // order that this singleton will be destroyed on shutdown. Therefore
    // do not remove any registered components.
}

// ***********************************************************************
// ***********************************************************************
const RelationshipSql& RelationshipSqlRepository::getRelationshipSql(const std::string& domainName, const std::string& relationshipName) const
{
    const std::string relationshipKey(domainName + "_" + relationshipName);
    std::map<std::string, const RelationshipSql* const>::const_iterator relSqlItr = repository.find(relationshipKey);
    if (relSqlItr == repository.end()){
        throw SqlException(std::string("RelationshipSqlRepository::getRelationshipSql : Failed to find SQL details for relationship ") + relationshipKey);
    }
    return *(relSqlItr->second);
}


} // end namespace SQL
