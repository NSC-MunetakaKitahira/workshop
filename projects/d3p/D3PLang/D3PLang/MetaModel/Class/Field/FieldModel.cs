using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Text;
using static D3PLang.MetaModel.GrammarUtil;

namespace D3PLang.MetaModel.Class.Field
{
    public class FieldModel
    {
        private const string TermName = "Field";

        public static NonTerminal Grammar(DomainModelGrammar g)
        {
            var self = NonTerminal(TermName);

            var nameWithType = NonTerminal("FieldNameWithType");
            nameWithType.Rule = IdJapanese("FieldName") + g.Colon + g.TypeSpecifier;

            self.Rule = g.AccessSpecifier + (g.TypeSpecifier | nameWithType);

            return self;
        }

        public string Type { get; }

        public string Name { get; }

        public FieldModel(ParseTreeNode self)
        {
            CheckNode(self, TermName);


        }
    }
}
