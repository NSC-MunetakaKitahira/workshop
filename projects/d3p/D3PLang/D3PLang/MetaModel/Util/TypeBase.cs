using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace D3PLang.MetaModel.Util
{
    public abstract class TypeBase
    {
        public string TermName { get; }

        protected TypeBase(string termName)
        {
            TermName = termName;
        }

        protected static Dictionary<string, T> CreateMap<T>(params T[] types)
            where T : TypeBase
        {
            return types.ToDictionary(t => t.TermName);
        }

        protected static BnfExpression Grammer(DomainModelGrammar g, IEnumerable<string> terms)
        {
            if (terms.Count() == 0) throw new Exception("1つ以上渡してね");

            int count = 0;
            BnfExpression expression = null;
            foreach (var term in terms)
            {
                if (count == 0)
                {
                    expression = g.ToTerm(term);
                }
                else
                {
                    expression |= term;
                }

                count++;
            }

            return expression;
        }
    }
}
