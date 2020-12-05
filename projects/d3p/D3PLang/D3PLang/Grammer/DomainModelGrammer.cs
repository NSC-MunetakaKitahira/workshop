using Irony.Parsing;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Text;

namespace D3PLang.Grammer
{
    [Language("D3PGrammar")]
    public class DomainModelGrammer : Grammar
    {
        public DomainModelGrammer()
        {
            var num = new NumberLiteral("Number");
            var str = new StringLiteral("String", "\"");

            var dot = ToTerm(".");
            var colon = ToTerm(":");

            var accessSpecifier = ToTerm("+") | "#" | "-";
            var mutable = ToTerm("mutable");
            var staticSpecifier = ToTerm("static");


            // type specifier
            var typeSpecifier = new NonTerminal("TypeSpecifier");
            {
                var typeName = IdJapanese("TypeName");
                typeSpecifier.Rule = MakePlusRule(typeSpecifier, dot, typeName);
            }

            var classBlock = new NonTerminal("ClassBlock");
            {
                // class define
                var classDefine = new NonTerminal("ClassDefine");
                {
                    var stereoType = new NonTerminal("StereoType");
                    stereoType.Rule = ToTerm("aggregate") | "service";
                    classDefine.Rule = stereoType + IdJapanese("ClassName");
                }

                // resp
                var respBlock = new NonTerminal("RespBlock");
                {
                    var restTextLines = new NonTerminal("RespTextLines");
                    {
                        var respText = FreeText("RespText");
                        restTextLines.Rule = MakePlusRule(restTextLines, respText);
                    }

                    respBlock.Rule = ToTerm("resp") + restTextLines;
                }

                // methods
                var methodsBlock = new NonTerminal("MethodsBlock");
                {
                    var methods = new NonTerminal("Methods");
                    {
                        // method
                        var method = new NonTerminal("Method");
                        {
                            var methodName = IdJapanese("MethodName");

                            var argsBlock = new NonTerminal("ArgsBlock");
                            {
                                var args = new NonTerminal("Args");
                                {
                                    var arg = new NonTerminal("Arg");
                                    arg.Rule = IdJapanese("ArgName").Q() + colon + typeSpecifier;

                                    args.Rule = MakeStarRule(args, arg);
                                }

                                argsBlock.Rule = ToTerm("args") + args;
                            }

                            method.Rule = accessSpecifier + methodName + argsBlock;
                        }

                        methods.Rule = MakeStarRule(methods, method);
                    }

                    methodsBlock.Rule = ToTerm("methods") + methods;
                }

                classBlock.Rule = classDefine + respBlock.Q() + methodsBlock;
            }

            Root = classBlock;
        }

        private static IdentifierTerminal IdJapanese(string name)
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

        private static IdentifierTerminal FreeText(string name)
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
    }
}
