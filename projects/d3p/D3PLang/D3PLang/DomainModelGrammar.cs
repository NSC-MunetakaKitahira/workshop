using D3PLang.MetaModel;
using D3PLang.MetaModel.Class.Resp;
using D3PLang.MetaModel.Method;
using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Net.Http.Headers;
using System.Runtime.InteropServices;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using static D3PLang.MetaModel.GrammarUtil;

namespace D3PLang
{
    [Language("D3PGrammar")]
    public class DomainModelGrammar : Grammar
    {
        public KeyTerm Dot { get; }

        public KeyTerm Comma { get; }

        public KeyTerm Colon { get; }

        public KeyTerm BracesIn { get; }

        public KeyTerm BracesOut { get; }

        public KeyTerm ParenthesisIn { get; }

        public KeyTerm ParenthesisOut { get; }

        public NonTerminal TypeSpecifier { get; }

        public BnfExpression AccessSpecifier { get; }


        public DomainModelGrammar()
        {
            Dot = ToTerm(".");
            Comma = ToTerm(",");
            Colon = ToTerm(":");

            BracesIn = ToTerm("{");
            BracesOut = ToTerm("}");

            ParenthesisIn = ToTerm("(");
            ParenthesisOut = ToTerm(")");

            AccessSpecifier = MetaModel.AccessSpecifier.Grammar(this);

            TypeSpecifier = new NonTerminal("TypeSpecifier");
            {
                var typeName = IdJapanese("TypeName");
                TypeSpecifier.Rule = MakePlusRule(TypeSpecifier, Dot, typeName);
            }

            //Root = DefineStatement();

            Root = ClassModel.Grammar(this);

        }

        /// <summary>
        /// ステートメントの定義・・・作りかけ
        /// </summary>
        private NonTerminal DefineStatement()
        {
            var Statement = new NonTerminal("Statement");
            var Expression = new NonTerminal("Expression");
            var MethodSpecifier = new NonTerminal("MethodSpecifier");
            var Call = new NonTerminal("Call");

            var Bool = ToTerm("true") | "false";
            var Num = new NumberLiteral("Number");
            var Str = new StringLiteral("String", "\"");
            var Null = ToTerm("null");
            var Value = new NonTerminal("Value");
            var Operand = new NonTerminal("Operand");

            var ArithmeticOperator = ToTerm("+") | "-" | "*" | "/";
            var ComparisonOperator = ToTerm("==") | "<>" | "<" | "<=" | ">" | ">=";
            var BinaryOperator = new NonTerminal("BinaryOperator");
            BinaryOperator.Rule = ArithmeticOperator | ComparisonOperator;

            var Tuple = new NonTerminal("Tuple");
            var TupleItems = new NonTerminal("TupleItems");

            var LocalVariable = new NonTerminal("LocalVariable");
            LocalVariable.Rule = ToTerm("$") + IdJapanese("LocalVariableName");

            var InputVariable = new NonTerminal("InputVariable");
            InputVariable.Rule = IdJapanese("InputVariableName");

            var FieldVariable = new NonTerminal("FieldVariable");
            FieldVariable.Rule = IdJapanese("FieldVariableName");

            Value.Rule = Bool | Num | Str | Null;
            Operand.Rule = Value | LocalVariable | InputVariable | FieldVariable;
            MethodSpecifier.Rule = TypeSpecifier;
            Call.Rule = MethodSpecifier | (MethodSpecifier + Tuple);

            Expression.Rule
                = Call
                | Value
                | (ParenthesisIn + Expression + ParenthesisOut);

            TupleItems.Rule = MakeStarRule(TupleItems, Comma, Expression);
            Tuple.Rule = ParenthesisIn + TupleItems + ParenthesisOut;

            Statement.Rule = Expression;

            return Statement;
        }

        //public override void CreateTokenFilters(LanguageData language, TokenFilterList filters)
        //{
        //    var outlineFilter = new CodeOutlineFilter(
        //        language.GrammarData,
        //        OutlineOptions.ProduceIndents | OutlineOptions.CheckBraces,
        //        ToTerm(@"\")); // "\" is continuation symbol
        //    filters.Add(outlineFilter);
        //}

    }
}
