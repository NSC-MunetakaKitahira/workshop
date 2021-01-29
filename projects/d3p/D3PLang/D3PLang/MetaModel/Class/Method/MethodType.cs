using D3PLang.MetaModel.Util;
using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace D3PLang.MetaModel.Class.Method
{
    public class MethodType : TypeBase
    {
        // 1個以上の引数だけを参照して結果を返す静的メソッド
        // updateを呼び出せない
        public static readonly MethodType Function = new MethodType("function");

        // 0個以上の引数と1個以上の属性を参照して結果を返すインスタンスメソッド
        // updateを呼び出せない
        public static readonly MethodType Query = new MethodType("query");

        // 0個以上の引数を参照して属性を変更するインスタンスメソッド
        public static readonly MethodType Update = new MethodType("update");

        // 0個以上の引数を参照してインスタンスを生成する静的メソッド
        // updateを呼び出せない
        public static readonly MethodType Build = new MethodType("build");

        private static readonly Dictionary<string, MethodType> Map = TypeBase.CreateMap(
            Function, Query, Update, Build);

        public static MethodType Of(string termName) => Map[termName];

        private MethodType(string termName) : base(termName)
        {
        }

        public static BnfExpression Grammar(DomainModelGrammar g) => Grammer(g, Map.Keys);
    }
}
