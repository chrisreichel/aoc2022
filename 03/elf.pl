#!/usr/bin/env perl

use strict;
use warnings;

my %letter_values;

#these are the ASCII intervals for a-z A-Z
for(((65..90),(97..122))){
    my $ascii_val = $_;
    my $char = chr($ascii_val);
    my $priority;
    if ($char =~ /[A-Z]/) {
        $priority  = $ascii_val - 64 + 26;
    }
    else{
        $priority  = $ascii_val - 70 - 26;
    }
    $letter_values{$char} = $priority;
    #print("ASCII $ascii_val is char: $char with elf priority $priority\n");
}

my $filename = 'sample.txt';

open(FH, '<', $filename) or die $!;

sub calculate_findings{

    my ($searcheable,$on_string_content) = @_;

    my $sum_of_priorities = 0;

    my @searcheable_letters = split //, $searcheable;
    my @content = split //, $on_string_content;

    my %found;
    for(@searcheable_letters){
        my $letter = $_;
        if ( !$found{$letter} && grep( /^$letter$/, @content ) ) {
            my $p = $letter_values{$letter};
            $sum_of_priorities += $p;
            $found{$letter} = 1;
            #print "found $letter with value $p on ";
        }
    }
    return $sum_of_priorities;
}


my $sum_of_priorities=0;
while(<FH>){
    my $line = $_;
    chomp $line;
    my $line_size = length $line;

    my $first_half = substr $line, 0, ($line_size/2);
    my $second_half = substr $line, ($line_size/2), $line_size;

    $sum_of_priorities += calculate_findings($first_half, $second_half);

    #print("line $line size of $line_size - parts: $first_half and $second_half - priorities $sum_of_priorities\n");

}
close(FH);

print "First part $sum_of_priorities\n";


