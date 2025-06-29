#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
JSON to LaTeX Converter for Mathematical Formulas

This script converts a JSON file containing mathematical formulas 
with English and Chinese descriptions into a well-formatted LaTeX document.

Author: Auto-generated
Date: 2025-06-29
"""

import json
import os
from typing import List, Dict


def load_json_formulas(json_file_path: str) -> List[Dict]:
    """
    Load mathematical formulas from a JSON file.
    
    Args:
        json_file_path (str): Path to the JSON file containing formulas
        
    Returns:
        List[Dict]: List of formula dictionaries
        
    Raises:
        FileNotFoundError: If the JSON file doesn't exist
        json.JSONDecodeError: If the JSON file is malformed
    """
    try:
        with open(json_file_path, 'r', encoding='utf-8') as file:
            formulas = json.load(file)
        return formulas
    except FileNotFoundError:
        raise FileNotFoundError(f"JSON file not found: {json_file_path}")
    except json.JSONDecodeError as e:
        raise json.JSONDecodeError(f"Invalid JSON format: {e}")


def escape_latex_special_chars(text: str) -> str:
    """
    Escape special LaTeX characters in text and convert Unicode math symbols.
    
    Args:
        text (str): Input text that may contain special characters
        
    Returns:
        str: Text with escaped special characters and converted math symbols
    """
    # First handle Unicode mathematical symbols
    math_symbols = {
        'π': r'$\pi$',
        'α': r'$\alpha$',
        'β': r'$\beta$', 
        'γ': r'$\gamma$',
        'δ': r'$\delta$',
        'ε': r'$\varepsilon$',
        'ζ': r'$\zeta$',
        'η': r'$\eta$',
        'θ': r'$\theta$',
        'ι': r'$\iota$',
        'κ': r'$\kappa$',
        'λ': r'$\lambda$',
        'μ': r'$\mu$',
        'ν': r'$\nu$',
        'ξ': r'$\xi$',
        'ο': r'$o$',
        'ρ': r'$\rho$',
        'σ': r'$\sigma$',
        'τ': r'$\tau$',
        'υ': r'$\upsilon$',
        'φ': r'$\phi$',
        'χ': r'$\chi$',
        'ψ': r'$\psi$',
        'ω': r'$\omega$',
        'Α': r'$A$',
        'Β': r'$B$',
        'Γ': r'$\Gamma$',
        'Δ': r'$\Delta$',
        'Ε': r'$E$',
        'Ζ': r'$Z$',
        'Η': r'$H$',
        'Θ': r'$\Theta$',
        'Ι': r'$I$',
        'Κ': r'$K$',
        'Λ': r'$\Lambda$',
        'Μ': r'$M$',
        'Ν': r'$N$',
        'Ξ': r'$\Xi$',
        'Ο': r'$O$',
        'Π': r'$\Pi$',
        'Ρ': r'$P$',
        'Σ': r'$\Sigma$',
        'Τ': r'$T$',
        'Υ': r'$\Upsilon$',
        'Φ': r'$\Phi$',
        'Χ': r'$X$',
        'Ψ': r'$\Psi$',
        'Ω': r'$\Omega$',
        '∞': r'$\infty$',
        '∑': r'$\sum$',
        '∏': r'$\prod$',
        '∫': r'$\int$',
        '∂': r'$\partial$',
        '∇': r'$\nabla$',
        '√': r'$\sqrt{}$',
        '≤': r'$\leq$',
        '≥': r'$\geq$',
        '≠': r'$\neq$',
        '≈': r'$\approx$',
        '≡': r'$\equiv$',
        '±': r'$\pm$',
        '∓': r'$\mp$',
        '×': r'$\times$',
        '÷': r'$\div$',
        '∘': r'$\circ$',
        '∈': r'$\in$',
        '∉': r'$\notin$',
        '⊂': r'$\subset$',
        '⊃': r'$\supset$',
        '⊆': r'$\subseteq$',
        '⊇': r'$\supseteq$',
        '∪': r'$\cup$',
        '∩': r'$\cap$',
        '∅': r'$\emptyset$',
        '∀': r'$\forall$',
        '∃': r'$\exists$',
        '¬': r'$\neg$',
        '∧': r'$\wedge$',
        '∨': r'$\vee$',
        '→': r'$\rightarrow$',
        '←': r'$\leftarrow$',
        '↔': r'$\leftrightarrow$',
        '⇒': r'$\Rightarrow$',
        '⇐': r'$\Leftarrow$',
        '⇔': r'$\Leftrightarrow$',
        '°': r'${}^{\circ}$',
        '′': r'${}^{\prime}$',
        '″': r'${}^{\prime\prime}$',
        '℃': r'${}^{\circ}$C',
        '℉': r'${}^{\circ}$F',
    }
    
    # Replace mathematical symbols first
    for symbol, latex_code in math_symbols.items():
        text = text.replace(symbol, latex_code)
    
    # Then handle common LaTeX special characters that need escaping in text mode
    special_chars = {
        '#': r'\#',
        '$': r'\$',
        '%': r'\%',
        '&': r'\&',
        '_': r'\_',
        '{': r'\{',
        '}': r'\}',
        '^': r'\textasciicircum{}',
        '~': r'\textasciitilde{}',
        '\\': r'\textbackslash{}'
    }
    
    # Replace special characters (but be careful not to break the math symbols we just added)
    # We need to avoid replacing $ that are part of math mode
    temp_text = text
    math_mode_count = 0
    result = ""
    i = 0
    
    while i < len(temp_text):
        char = temp_text[i]
        
        # Track if we're in math mode
        if char == '$':
            math_mode_count += 1
            result += char
        elif char in special_chars and math_mode_count % 2 == 0:  # Not in math mode
            result += special_chars[char]
        else:
            result += char
        i += 1
    
    return result


def generate_latex_document(formulas: List[Dict], output_file: str) -> None:
    """
    Generate a LaTeX document from the list of formulas.
    
    Args:
        formulas (List[Dict]): List of formula dictionaries
        output_file (str): Path to the output LaTeX file
    """
    # LaTeX document preamble with Chinese support and Unicode math symbols
    latex_content = r"""\documentclass[12pt,a4paper]{article}
\usepackage[utf8]{inputenc}
\usepackage{amsmath}
\usepackage{amsfonts}
\usepackage{amssymb}
\usepackage{geometry}
\usepackage{xeCJK}
\usepackage{fontspec}
\usepackage{enumitem}
\usepackage{titlesec}
\usepackage{fancyhdr}
\usepackage{hyperref}
\usepackage{unicode-math}  % Better Unicode math support

% Set up Chinese fonts (requires XeLaTeX)
\setCJKmainfont{SimSun}

% Set paragraph indentation for Chinese text
\setlength{\parindent}{2em}  % 设置段落首行缩进为2个字符宽度

% Page setup
\geometry{margin=2.5cm}
\pagestyle{fancy}
\fancyhf{}
\fancyhead[C]{经典数学公式集 (Classic Mathematical Formulas)}
\fancyfoot[C]{\thepage}

% Title formatting
\titleformat{\section}{\Large\bfseries}{\thesection}{1em}{}
\titleformat{\subsection}{\large\bfseries}{\thesubsection}{1em}{}

% Hyperref setup
\hypersetup{
    colorlinks=true,
    linkcolor=blue,
    filecolor=magenta,      
    urlcolor=cyan,
    pdftitle={Classic Mathematical Formulas},
    pdfauthor={Auto-generated},
    pdfsubject={Mathematics},
    pdfkeywords={mathematics, formulas, LaTeX}
}

\begin{document}

% Title page
\begin{titlepage}
\centering
\vspace*{2cm}
{\Huge\bfseries 经典数学公式集}\\[0.5cm]
{\Large\bfseries Classic Mathematical Formulas}\\[2cm]
{\large 包含中英文对照的数学公式汇编}\\[0.3cm]
{\large A Collection of Mathematical Formulas with Chinese and English Descriptions}\\[3cm]
{\large 编译日期 (Compiled Date): """ + r"""\today}\\[2cm]
\vfill
{\large 本文档由Python脚本自动生成}\\
{\large This document was automatically generated by a Python script}
\end{titlepage}

\newpage

\section{数学公式 (Mathematical Formulas)}

\begin{enumerate}[leftmargin=*]
"""

    # Add each formula as an enumerated item
    for i, formula in enumerate(formulas, 1):
        latex_content += f"\n\\item \\textbf{{公式 {i} (Formula {i})}}\n\n"
        
        # Add the mathematical formula
        latex_content += f"\\begin{{equation}}\n{formula['formula']}\n\\end{{equation}}\n\n"
        
        # Add descriptions
        chinese_desc = escape_latex_special_chars(formula['chinese'])
        english_desc = escape_latex_special_chars(formula['english'])
        
        latex_content += f"\\textbf{{中文说明:}} {chinese_desc}\n\n"
        latex_content += f"\\textbf{{English Description:}} {english_desc}\n\n"
        
        # Add some spacing between formulas
        if i < len(formulas):
            latex_content += "\\vspace{0.5cm}\n"

    # Document closing
    latex_content += r"""
\end{enumerate}

\end{document}
"""

    # Write the LaTeX content to file
    try:
        with open(output_file, 'w', encoding='utf-8') as file:
            file.write(latex_content)
        print(f"✓ LaTeX文档已成功生成: {output_file}")
        print(f"✓ LaTeX document successfully generated: {output_file}")
    except IOError as e:
        raise IOError(f"无法写入LaTeX文件 (Cannot write LaTeX file): {e}")


def main():
    """
    Main function to convert JSON formulas to LaTeX document.
    """
    # File paths
    json_file = "classic_math_formulas.json"
    latex_file = "math_formulas.tex"
    
    print("=" * 60)
    print("数学公式JSON转LaTeX转换器")
    print("Mathematical Formulas JSON to LaTeX Converter")
    print("=" * 60)
    
    try:
        # Check if JSON file exists
        if not os.path.exists(json_file):
            print(f"❌ 错误: 找不到JSON文件 '{json_file}'")
            print(f"❌ Error: JSON file '{json_file}' not found")
            return
        
        # Load formulas from JSON
        print(f"📖 正在读取JSON文件: {json_file}")
        print(f"📖 Reading JSON file: {json_file}")
        formulas = load_json_formulas(json_file)
        print(f"✓ 成功加载 {len(formulas)} 个数学公式")
        print(f"✓ Successfully loaded {len(formulas)} mathematical formulas")
        
        # Generate LaTeX document
        print(f"📝 正在生成LaTeX文档: {latex_file}")
        print(f"📝 Generating LaTeX document: {latex_file}")
        generate_latex_document(formulas, latex_file)
        print(f"✓ 转换完成!")
        print(f"✓ Conversion completed!")
        
        # Provide compilation instructions
        print("\n" + "=" * 60)
        print("编译说明 (Compilation Instructions):")
        print("=" * 60)
        print("使用以下命令编译LaTeX文档 (Use the following commands to compile):")
        print(f"  xelatex {latex_file}")
        print(f"  xelatex {latex_file}  # 运行两次生成目录 (Run twice for TOC)")
        print("\n注意: 需要安装XeLaTeX和中文字体支持")
        print("Note: Requires XeLaTeX and Chinese font support")
        
    except Exception as e:
        print(f"❌ 转换过程中发生错误: {e}")
        print(f"❌ Error during conversion: {e}")


if __name__ == "__main__":
    main()
