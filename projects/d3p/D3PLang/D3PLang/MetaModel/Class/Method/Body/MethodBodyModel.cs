using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using static D3PLang.MetaModel.GrammarUtil;

namespace D3PLang.MetaModel.Class.Method.Body
{
    public class MethodBodyModel
    {
        private const string TermName = "MethodBody";

        public static NonTerminal Grammer(DomainModelGrammar g)
        {
            var self = NonTerminal(TermName);

            var content = NonTerminal("MethodBodyContent");
            {

            }

            return self;
        }
    }
}
