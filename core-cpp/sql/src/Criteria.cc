//============================================================================//
// UK Crown Copyright (c) 2007. All rights reserved.
//
// File:   Criteria.cc
//
//============================================================================//

#include "sql/Criteria.hh"
#include "sql/CriteriaFactory.hh"

namespace SQL {

// ***********************************************************************
// ***********************************************************************
Criteria::Criteria():
   impl_(CriteriaFactory::singleton().newInstance())
{

}

// ***********************************************************************
// ***********************************************************************
Criteria::~Criteria()
{

}

// ***********************************************************************
// ***********************************************************************
bool Criteria::empty() const
{
  return impl_->empty();
}

// ***********************************************************************
// ***********************************************************************
void Criteria::setLimit (const int32_t limit)
{
   impl_->setLimit(limit);
}

// ***********************************************************************
// ***********************************************************************
void Criteria::addFromClause (const std::string& tableName)
{
    impl_->addFromClause(tableName);
}

// ***********************************************************************
// ***********************************************************************
void Criteria::addWhereClause (const std::string& where)
{
    impl_->addWhereClause(where);
}

// ***********************************************************************
// ***********************************************************************
void Criteria::addColumn (const std::string& columnName)
{
   impl_->addColumn(columnName);
}

// ***********************************************************************
// ***********************************************************************
void Criteria::addAllColumn ()
{
   impl_->addAllColumn();
}

// ***********************************************************************
// ***********************************************************************
void Criteria::addAllColumns (const std::string& tableName)
{
   impl_->addAllColumns(tableName);
}

// ***********************************************************************
// ***********************************************************************
std::string Criteria::selectStatement() const
{
    return impl_->selectStatement();
}


} // end namespace SQL


