using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Text;

namespace D3PLang.MetaModel
{
    public static class GrammarUtil
    {
        public static NonTerminal NonTerminal(string name)
        {
            return new NonTerminal(name);
        }

        /// <summary>
        /// 日本語を含む識別子
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        public static IdentifierTerminal IdJapanese(string name)
        {
            var letters = new[]
            {
                UnicodeCategory.UppercaseLetter,
                UnicodeCategory.LowercaseLetter,
                UnicodeCategory.OtherLetter,
                UnicodeCategory.LetterNumber,
                UnicodeCategory.DashPunctuation,
                UnicodeCategory.ModifierLetter,
            };

            var id = new IdentifierTerminal(name);
            id.StartCharCategories.AddRange(letters);
            id.CharCategories.AddRange(letters);

            return id;
        }

        /// <summary>
        /// 特に制限の無いテキスト。説明文とか。
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        public static IdentifierTerminal FreeText(string name)
        {
            var letters = new[]
{
                UnicodeCategory.UppercaseLetter,
                UnicodeCategory.LowercaseLetter,
                UnicodeCategory.OtherLetter,
                UnicodeCategory.LetterNumber,
                UnicodeCategory.DashPunctuation,
                UnicodeCategory.ModifierLetter,
                UnicodeCategory.OtherPunctuation,
            };

            var id = new IdentifierTerminal(name);
            id.StartCharCategories.AddRange(letters);
            id.CharCategories.AddRange(letters);

            return id;
        }

        public static void CheckNode(ParseTreeNode node, string termName)
        {
            if (node.Term.Name != termName)
            {
                throw new Exception($"invalid term name: '{node.Term.Name}' is not '{termName}'");
            }
        }

        public static M ParseQ<M>(ParseTreeNode nodeQ, Func<ParseTreeNode, M> parser)
        {
            if (nodeQ.ChildNodes[0].ChildNodes.Count == 1)
            {
                return parser(nodeQ.ChildNodes[0].ChildNodes[0]);
            }

            return default(M);
        }
    }
}
