#!/bin/bash

FILE=input.txt

num_of_fully_overlaps=0
num_of_partial_overlaps=0

for line in `cat $FILE | sed 's/,/-/g'`; do
    arrSECTIONS=(${line//-/ })
    first_sequence=$(seq -s ' ' ${arrSECTIONS[0]} ${arrSECTIONS[1]} | sed 's/ *$//g')
    second_sequence=$(seq -s ' ' ${arrSECTIONS[2]} ${arrSECTIONS[3]} | sed 's/ *$//g')
    first_sequence_array=($first_sequence)
    second_sequence_array=($second_sequence)
    size_of_if_no_overlap=$((${#first_sequence_array[@]} + ${#second_sequence_array[@]}))
    full=$(echo "$first_sequence $second_sequence")
    sorted=$(echo $full | xargs -n1 | sort | uniq | xargs)
    sorted_array=($sorted)
    result="none"
    if [ ${#first_sequence_array[@]} -eq  ${#sorted_array[@]} ] || [ ${#second_sequence_array[@]} -eq  ${#sorted_array[@]} ]
    then
        num_of_fully_overlaps=$((num_of_fully_overlaps+1))
        num_of_partial_overlaps=$((num_of_partial_overlaps+1))
        result="FULLY_OVERLAP"
    elif [  ${#sorted_array[@]} -lt $size_of_if_no_overlap ]
    then
        num_of_partial_overlaps=$((num_of_partial_overlaps+1))
        result="PARTIAL_OVERLAP"
    fi
    #echo "${arrSECTIONS[0]}-${arrSECTIONS[1]},${arrSECTIONS[2]}-${arrSECTIONS[3]}:$result"
    #echo "--"
done

echo "Full overlaps: $num_of_fully_overlaps"
echo "Partial overlaps: $num_of_partial_overlaps"