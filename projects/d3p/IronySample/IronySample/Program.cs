using Irony.Parsing;
using IronySamleGrammer.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IronySample
{
    class Program
    {
        static void Main(string[] args)
        {
            var grammer = new IronySamleGrammer.GrammarDefinition();

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
                var classBlock = parseTree.Root;
                var classModel = new ClassModel(classBlock);

                parseTree.ToString();
            } else
            {
                parseTree.ToString();
            }


        }
    }
}
