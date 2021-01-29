using D3PLang.MetaModel.Class.Method;
using D3PLang.MetaModel.Method.Input;
using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Text;
using static D3PLang.MetaModel.GrammarUtil;

namespace D3PLang.MetaModel.Method
{
    public class MethodModel
    {
        private const string TermName = "Method";

        public static NonTerminal Grammer(DomainModelGrammar g)
        {
            var self = NonTerminal(TermName);

            var methodType = MethodType.Grammar(g);
            var methodName = IdJapanese("MethodName");
            var input = InputModel.Grammar(g);

            self.Rule = g.AccessSpecifier + methodType + methodName
                + g.BracesIn
                + input.Q()
                + g.BracesOut;

            return self;
        }

        public MethodType Type { get; }

        public string Name { get; }

        public AccessSpecifier AccessSpecifier { get; }

        public InputModel Input { get; }
        
        public MethodModel(ParseTreeNode self)
        {
            CheckNode(self, TermName);

            string accessSpecifierText = self.ChildNodes[0].ChildNodes[0].Token.Text;
            AccessSpecifier = AccessSpecifier.Of(accessSpecifierText);

            string typeText = self.ChildNodes[1].ChildNodes[0].Token.Text;
            Type = MethodType.Of(typeText);

            Name = self.ChildNodes[2].Token.Text;

            Input = ParseQ(self.ChildNodes[4], n => new InputModel(n));
        }
    }
}
