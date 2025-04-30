package umletConverter_class;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * umlに変換するクラス
 */
public class ConverterUMLet {
    private String code;
    private String className;

    /**
     * コンストラクタ
     * @param code コードのコピペ
     */
    public ConverterUMLet(String code) {
        this.code = code;
    }

    /**
     * 変換
     */
    public String convertCode() {
        String classString = extractClassName();
        String fields = extractFields();
        String constructors = extractConstructors(className);
        String methods = extractMethods(className); // クラス名をメソッドに渡す

        return (classString + "\n--\n" + fields + "\n--\n" + constructors + methods);
    }

    /**
     * クラス名どうにかしてくれる
     * @return
     */
    private String extractClassName() {

        Pattern pattern = Pattern.compile("(public|private|protected|abstract|interface)" + "\\s+"
                + "(interface|class)" + "\\s+" + "(\\w+)");
        Matcher matcher = pattern.matcher(code);

        if (matcher.find()) {
            String visibility = matcher.group(1);
            String classType = matcher.group(2);
            className = matcher.group(3);

            if (classType.equals("class") && visibility.equals("abstract")) {
                return "/" + className + "/";
            } else if (classType.equals("interface")) {
                return "<<interface>>\n" + className;
            } else {
                return className;
            }
        }
        return "UnknownClass";
    }

    /**
     * フィールド変数どうにかしてくれるメソッド
     * @return
     */
    private String extractFields() {
        // フィールド定義用の正規表現パターン
        Pattern pattern = Pattern
                .compile("(public|private|protected)?\\s*(static)?\\s*(\\w+<\\w+>\\s*|\\w+)\\s+(\\w+)\\s*;");
        Matcher matcher = pattern.matcher(code);
        StringBuilder umlFields = new StringBuilder();

        while (matcher.find()) {
            String accessModifierString = matcher.group(1); // アクセス修飾子
            String staticModifier = matcher.group(2); // static修飾子
            String fieldType = matcher.group(3); // フィールドの型
            String fieldName = matcher.group(4); // フィールド名

            // アクセス修飾子の可視性設定
            String visibility = "~"; // デフォルトはパッケージプライベート
            if (accessModifierString != null) {
                if (accessModifierString.equals("public")) {
                    visibility = "+";
                } else if (accessModifierString.equals("private")) {
                    visibility = "-";
                } else if (accessModifierString.equals("protected")) {
                    visibility = "#";
                }
            }

            // フィールドの組み立て（staticで囲むかどうか）
            if (!fieldType.equals("return")) {
                if (staticModifier != null && staticModifier.equals("static")) {
                    umlFields.append("_")
                            .append(visibility)
                            .append(" ")
                            .append(fieldName)
                            .append(":")
                            .append(fieldType)
                            .append("_\n");
                } else {
                    umlFields.append(visibility)
                            .append(" ")
                            .append(fieldName)
                            .append(":")
                            .append(fieldType)
                            .append("\n");
                }
            }
        }

        return umlFields.toString().trim(); // 最後の余分な改行を削除
    }

    /**
     *　コンストラクタどうにかするメソッド
     * @param className
     * @return
     */
    private String extractConstructors(String className) {

        Pattern pattern = Pattern.compile("(public|private|protected)?\\s*" + className + "\\(([^)]*)\\)\\s*\\{");
        Matcher matcher = pattern.matcher(code);
        StringBuilder umlConstructors = new StringBuilder();

        while (matcher.find()) {
            String accessModifier = matcher.group(1);
            String arguments = matcher.group(2);

            String visibility = "~";
            if ("public".equals(accessModifier)) {
                visibility = "+";
            } else if ("private".equals(accessModifier)) {
                visibility = "-";
            } else if ("protected".equals(accessModifier)) {
                visibility = "#";
            }

            // コンストラクタの出力処理
            umlConstructors.append(visibility)
                    .append(" ")
                    .append(className)
                    .append("(")
                    .append(formatArguments(arguments))
                    .append(")\n");
        }

        return umlConstructors.toString();
    }

    /**
     * メソッド拾ってどうにかしてくれる
     * @param className
     * @return
     */
    private String extractMethods(String className) {
        Pattern pattern = Pattern.compile(
                "(public|private|protected)?\\s*(static|abstract)?\\s*(\\w+<\\w+>|\\w+)?\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\(([^)]*)\\)\\s*"
        );

        Matcher matcher = pattern.matcher(code);
        StringBuilder umlMethods = new StringBuilder();

        while (matcher.find()) {
            String accessModifier = matcher.group(1);  // アクセス修飾子
            String staticAbstractModifier = matcher.group(2);  // static/abstract 修飾子
            String returnType = matcher.group(3);  // 戻り値の型
            String methodName = matcher.group(4);  // メソッド名
            String arguments = matcher.group(5);  // 引数

            // デバッグ用の出力
            System.out.println("Access Modifier: " + accessModifier);
            System.out.println("Static/Abstract Modifier: " + staticAbstractModifier);
            System.out.println("Return Type: " + returnType);
            System.out.println("Method Name: " + methodName);
            System.out.println("Arguments: " + arguments + "\n");

         // コンストラクタやsuper()の呼び出しをスキップ
            if (methodName.equals(className) || methodName.equals("super")) {
                continue;
            }

            // アクセス修飾子の判定
            String visibility = "~";
            if ("public".equals(accessModifier)) {
                visibility = "+";
            } else if ("private".equals(accessModifier)) {
                visibility = "-";
            } else if ("protected".equals(accessModifier)) {
                visibility = "#";
            }

            // メソッドの引数を整形
            String formattedArguments = formatArguments(arguments);

            // abstract メソッドの出力処理
            if ("abstract".equals(staticAbstractModifier)) {
                umlMethods.append("/")
                        .append(visibility)
                        .append(" ")
                        .append(methodName)
                        .append("(")
                        .append(formattedArguments)
                        .append("):")
                        .append(returnType != null ? returnType : "void")
                        .append("/\n");
            } else {
                // static メソッドの出力処理
                if ("static".equals(staticAbstractModifier)) {
                    umlMethods.append("_") // staticメソッドの囲み
                            .append(visibility)
                            .append(" ")
                            .append(methodName)
                            .append("(")
                            .append(formattedArguments)
                            .append("):")
                            .append(returnType != null ? returnType : "void")
                            .append("_\n");
                } else {
                    umlMethods.append(visibility)
                            .append(" ")
                            .append(methodName)
                            .append("(")
                            .append(formattedArguments)
                            .append("):")
                            .append(returnType != null ? returnType : "void")
                            .append("\n");
                }
            }
        }

        return umlMethods.toString().trim(); // 最後の余分な改行を削除
    }


    /**
     * 引数どうにかしてくれるメソッド
     * @param arguments
     * @return
     */
    private String formatArguments(String arguments) {
        StringBuilder formattedArguments = new StringBuilder();
        if (!arguments.isEmpty()) {
            String[] args = arguments.split(",");
            for (String arg : args) {
                String[] argParts = arg.trim().split("\\s+");
                if (argParts.length == 2) {
                    formattedArguments.append(argParts[1])
                            .append(":")
                            .append(argParts[0])
                            .append(", ");
                }
            }
            if (formattedArguments.length() > 0) {
                formattedArguments.setLength(formattedArguments.length() - 2); // 最後のカンマを削除
            }
        }
        return formattedArguments.toString();
    }
}
