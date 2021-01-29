using D3PLang.MetaModel.Util;
using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Text;

namespace D3PLang.MetaModel.Class
{

    public class ClassStereoType : TypeBase
    {
        public static readonly ClassStereoType AggregateRoot = new ClassStereoType("aggregate");
        public static readonly ClassStereoType ValueObject = new ClassStereoType("value");
        public static readonly ClassStereoType Temporary = new ClassStereoType("temporary");
        public static readonly ClassStereoType DomainService = new ClassStereoType("service");

        private static readonly Dictionary<string, ClassStereoType> Map = TypeBase.CreateMap(
            AggregateRoot, ValueObject, Temporary, DomainService);

        public static ClassStereoType Of(string termName) => Map[termName];

        private ClassStereoType(string termName) : base(termName)
        {
        }

        public static BnfExpression Grammar(DomainModelGrammar g) => Grammer(g, Map.Keys);
    }
}
