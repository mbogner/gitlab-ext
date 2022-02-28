#!/bin/bash
input="./gitlab.rb"
echo "----------------------------------------------"
echo "gitlab config"
echo "----------------------------------------------"
CNF='"'
while IFS= read -r line; do
  [[ $line =~ ^#.* ]] && continue
  [[ $line =~ ^$ ]] && continue
  CNF="${CNF}${line};"
done <"$input"
CNF="${CNF}\""
echo "$CNF"
echo "----------------------------------------------"
echo "$CNF" | pbcopy || exit 1
echo "copied to clipboard"
