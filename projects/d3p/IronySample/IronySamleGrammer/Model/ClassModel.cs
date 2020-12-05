using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IronySamleGrammer.Model
{
    public class ClassModel
    {
        public string Resp { get; }

        public ClassModel(ParseTreeNode classBlock)
        {
            var classDefine = classBlock.ChildNodes[0];
            string stereoTypeText = classDefine.ChildNodes[0].ChildNodes[0].Token.ValueString;


            Resp = "k";
        }
    }
}
