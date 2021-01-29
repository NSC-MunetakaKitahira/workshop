using Irony.Parsing;
using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using static D3PLang.MetaModel.GrammarUtil;

namespace D3PLang.MetaModel.Method.Input
{
    public class InputModel
    {
        private const string TermName = "InputBlock";
        
        public static NonTerminal Grammar(DomainModelGrammar g)
        {
            var self = NonTerminal(TermName);

            var items = NonTerminal("InputItems");
            {
                var item = InputItemModel.Grammar(g);
                items.Rule = g.MakeStarRule(items, item);
            }

            self.Rule = g.ToTerm("input") + g.BracesIn + items + g.BracesOut;

            return self;
        }

        public List<InputItemModel> List { get; }

        public InputModel(ParseTreeNode self)
        {
            CheckNode(self, TermName);

            var items = self.ChildNodes[2];

            List = items.ChildNodes.Select(n => new InputItemModel(n)).ToList();
        }
    }
}
