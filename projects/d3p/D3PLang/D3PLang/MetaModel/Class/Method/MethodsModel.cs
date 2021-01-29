using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using static D3PLang.MetaModel.GrammarUtil;

namespace D3PLang.MetaModel.Method
{
    public class MethodsModel
    {
        private const string TermName = "MethodsBlock";

        public static NonTerminal Grammer(DomainModelGrammar g)
        {
            var self = NonTerminal(TermName);

            var methods = NonTerminal("Methods");
            {
                // method
                var method = MethodModel.Grammer(g);

                methods.Rule = g.MakeStarRule(methods, method);
            }

            self.Rule = g.ToTerm("methods")
                + g.BracesIn
                + methods
                + g.BracesOut;

            return self;
        }

        public List<MethodModel> List { get; }

        public MethodsModel(ParseTreeNode methodsBlockNode)
        {
            if (methodsBlockNode.Term.Name != TermName)
            {
                throw new Exception(methodsBlockNode.Term.Name);
            }

            var methods = methodsBlockNode.ChildNodes[2].ChildNodes;

            List = methods.Select(n => new MethodModel(n)).ToList();
        }
    }
}
