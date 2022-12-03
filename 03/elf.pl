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
}

my $filename = 'input.txt';

open(FH, '<', $filename) or die $!;

sub calculate_findings{

    my @array_of_searches = @_;
    my $searches_size = @array_of_searches;

    my ($searcheable,$on_string_content) = ($array_of_searches[0], $array_of_searches[1]);

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
        }
    }

    if($searches_size == 2){
        return $sum_of_priorities;
    }
    else{
        my $found_letters = join ('', ( keys %found ));
        return calculate_findings($found_letters, $array_of_searches[2]);
    }
}


my $sum_of_priorities_part1=0;
my $sum_of_priorities_part2=0;
my $control_idx = 0;
my @temp_acc = ();
while(<FH>){
    $control_idx++;
    my $line = $_;
    chomp $line;
    my $line_size = length $line;

    my $first_half_part1 = substr $line, 0, ($line_size/2);
    my $second_half_part1 = substr $line, ($line_size/2), $line_size;

    $sum_of_priorities_part1 += calculate_findings($first_half_part1, $second_half_part1);

    if($control_idx % 3 == 0){
        push(@temp_acc, $line);
        $sum_of_priorities_part2 += calculate_findings(@temp_acc);
        @temp_acc = ();
    } else {
        push(@temp_acc, $line);
    }
}
close(FH);

print "First part $sum_of_priorities_part1\n";
print "Second part $sum_of_priorities_part2\n";


