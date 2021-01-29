using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Diagnostics.Contracts;
using System.Text;
using static D3PLang.MetaModel.GrammarUtil;

namespace D3PLang.MetaModel.Method.Input
{
    public class InputItemModel
    {
        private const string TermName = "InputItem";

        public static NonTerminal Grammar(DomainModelGrammar g)
        {
            var self = NonTerminal(TermName);

            var inputNameDeclare = IdJapanese("InputName");

            self.Rule = inputNameDeclare + g.Colon + g.TypeSpecifier;

            return self;
        }

        public string Type { get; }

        public string Name { get; }

        public InputItemModel(ParseTreeNode self)
        {
            CheckNode(self, TermName);

            Name = self.ChildNodes[0].Token.Text;
            Type = self.ChildNodes[1].Token.Text;

        }
    }
}
