using D3PLang.MetaModel.Class;
using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using D3PLang.MetaModel.Method;
using static D3PLang.MetaModel.GrammarUtil;
using D3PLang.MetaModel.Class.Resp;
using D3PLang.MetaModel.Class.Field;

namespace D3PLang.MetaModel
{
    public class ClassModel
    {

        private const string TermName = "ClassBlock";

        public static NonTerminal Grammar(DomainModelGrammar g)
        {
            var self = NonTerminal(TermName);

            // class define
            var classDefine = new NonTerminal("ClassDefine");
            {
                var stereoType = new NonTerminal("StereoType");
                stereoType.Rule = ClassStereoType.Grammar(g);

                classDefine.Rule = stereoType + IdJapanese("ClassName");
            }

            // resp
            var respBlock = RespModel.Grammar(g);

            // fields
            var fieldsBlock = FieldsModel.Grammar(g);

            // methods
            var methodsBlock = MethodsModel.Grammer(g);

            self.Rule = classDefine
                + g.BracesIn
                + respBlock
                + fieldsBlock.Q()
                + methodsBlock.Q()
                + g.BracesOut;

            return self;
        }
        public ClassStereoType StereoType { get; }

        public string Name { get; }

        public RespModel Resp { get; }

        public FieldsModel Fields { get; }

        public MethodsModel Methods { get; }

        public ClassModel(ParseTreeNode self)
        {
            CheckNode(self, TermName);

            var classDefine = self.ChildNodes[0];
            {
                var stereoType = classDefine.ChildNodes[0];
                string stereoTypeText = stereoType.ChildNodes[0].Token.Text;

                StereoType = ClassStereoType.Of(stereoTypeText);
            }

            Name = classDefine.ChildNodes[1].Token.Text;

            var respBlock = self.ChildNodes[2];
            Resp = new RespModel(respBlock);

            Fields = ParseQ(self.ChildNodes[3], n => new FieldsModel(n));

            Methods = ParseQ(self.ChildNodes[4], n => new MethodsModel(n));
        }
    }
}
