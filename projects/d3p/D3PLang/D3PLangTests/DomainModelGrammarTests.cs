using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Text;
using Irony.Parsing;
using D3PLang.MetaModel;

namespace D3PLang.Tests
{
    [TestClass()]
    public class DomainModelGrammarTests
    {
        [TestMethod()]
        public void DomainModelGrammerTest()
        {
            var grammer = new DomainModelGrammar();

            Parser parser = new Parser(grammer);

            string code2 = @"
value 利用停止状態
{
	resp
    {
		クラスの責務のせつめい
        いろいろ
    }

	fields
    {
		+ システム利用状態 : s
		+ 利用停止モード : c
		+ 停止予告のメッセージ : StopMessage
		+ 利用停止のメッセージ : StopMessage
    }

	methods
    {
		+ query ステータス
        {
			input
            {
				roles : ログインユーザロール
            }

			
        }
    }
}
";
            string code3 = "(利用停止状態.ok(a, b))";

            var parseTree = parser.Parse(code2);

            if (parseTree.Status == ParseTreeStatus.Parsed)
            {
                //new ClassModel(parseTree.Root);
                parseTree.ToString();
            }
            else
            {
                var error = parseTree.ParserMessages[0];
                Assert.Fail($"({error.Location.Line}行{error.Location.Column}列) {error.Message}");
                parseTree.ToString();
            }


        }
    }
}