using D3PLang.MetaModel.Util;
using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Text;

namespace D3PLang.MetaModel
{
    public class AccessSpecifier : TypeBase
    {
        public static readonly AccessSpecifier Public = new AccessSpecifier("+");
        public static readonly AccessSpecifier Protected = new AccessSpecifier("#");
        public static readonly AccessSpecifier Private = new AccessSpecifier("-");

        private static readonly Dictionary<string, AccessSpecifier> Map = TypeBase.CreateMap(
            Public, Protected, Private);

        public static AccessSpecifier Of(string termName) => Map[termName];

        private AccessSpecifier(string termName) : base(termName)
        {
        }

        public static BnfExpression Grammar(DomainModelGrammar g) => 
            g.ToTerm(Public.TermName) | Protected.TermName | Private.TermName;
    }
}
