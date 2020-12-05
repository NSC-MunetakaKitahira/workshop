using Microsoft.VisualStudio.TestTools.UnitTesting;
using D3PLang.Grammer;
using System;
using System.Collections.Generic;
using System.Text;
using Irony.Parsing;

namespace D3PLang.Grammer.Tests
{
    [TestClass()]
    public class DomainModelGrammerTests
    {
        [TestMethod()]
        public void DomainModelGrammerTest()
        {
            var grammer = new DomainModelGrammer();

            Parser parser = new Parser(grammer);

            string code = $@"
service システムが利用できるか
	resp
        システム利用停止の状況に応じて、
        指定のユーザがログインできるか判断する

	methods
		+ チェックする
			args
				: テナントコード
				: 会社ID
				対象ユーザID : ユーザID
";

            string code2 = $@"
service システムが利用できるか
";
            string code3 = "require";

            var parseTree = parser.Parse(code);

            if (parseTree.Status == ParseTreeStatus.Parsed)
            {
                
                parseTree.ToString();
            }
            else
            {
                parseTree.ToString();
            }


        }
    }
}