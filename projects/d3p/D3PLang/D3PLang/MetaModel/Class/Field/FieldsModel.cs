using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Text;
using static D3PLang.MetaModel.GrammarUtil;

namespace D3PLang.MetaModel.Class.Field
{
    public class FieldsModel
    {
        private const string TermName = "FieldsRegion";

        public static NonTerminal Grammar(DomainModelGrammar g)
        {
            var self = NonTerminal(TermName);

            var fields = NonTerminal("Fields");
            {
                var field = FieldModel.Grammar(g);
                fields.Rule = g.MakeStarRule(fields, field);
            }

            self.Rule = g.ToTerm("fields") + g.BracesIn + fields + g.BracesOut;

            return self;
        }

        public FieldsModel(ParseTreeNode self)
        {
            CheckNode(self, TermName);
        }
    }
}
