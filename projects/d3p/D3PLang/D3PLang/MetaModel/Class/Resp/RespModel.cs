using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using static D3PLang.MetaModel.GrammarUtil;

namespace D3PLang.MetaModel.Class.Resp
{
    public class RespModel
    {
        public List<string> Lines { get; }

        private const string TermName = "RespBlock";

        public static NonTerminal Grammar(DomainModelGrammar g)
        {
            var self = NonTerminal(TermName);

            var restTextLines = new NonTerminal("RespTextLines");
            {
                var respText = FreeText("RespText");
                restTextLines.Rule = g.MakePlusRule(restTextLines, respText);
            }

            self.Rule = g.ToTerm("resp") + g.BracesIn + restTextLines + g.BracesOut;

            return self;
        }

        public RespModel(ParseTreeNode self)
        {
            CheckNode(self, TermName);

            var respTexts = self.ChildNodes[2].ChildNodes;
            Lines = respTexts.Select(n => n.Token.Text).ToList();
        }
    }
}
