use std::fs::File;
use std::io::Read;

fn main() {
    test_dance();

    let mut f = File::open("../day16.in").expect("File not found");
    let mut moves = String::new();
    f.read_to_string(&mut moves).expect("Error reading file");
    println!("Part1: {}", dance("abcdefghijklmnop".to_string(), moves));
}

fn dance(lineup: String, raw_moves: String) -> String {
    let mut new_lineup = lineup;
    let moves: Vec<&str> = raw_moves.split(",").collect();
    for m in &moves {
        let action: String = m.chars().take(1).collect();
        let args_string: String = m.chars().skip(1).collect();
        match action.as_ref() {
            "s" => {
                let count = args_string.parse::<usize>().unwrap();
                new_lineup = spin(new_lineup, count);
            },
            "x" => {
                let args: Vec<&str> = args_string.split("/").collect();
                let a_index: usize = args[0].parse::<usize>().unwrap();
                let b_index: usize = args[1].parse::<usize>().unwrap();
                new_lineup = exchange(new_lineup, a_index, b_index)
            },
            "p" => {
                let args: Vec<&str> = args_string.split("/").collect();
                let a_program = args[0];
                let b_program = args[1];
                new_lineup = partner(new_lineup, a_program, b_program)
            },
            _x => panic!("Unexpected move")
        }
    }
    return new_lineup;
}

fn spin(lineup: String, count: usize) -> String {
    let mut spin_string = lineup;
    for _ in 0..count {
        let spin_char: String = spin_string.chars()
                                .skip(spin_string.len()-1).collect();
        spin_string = spin_string.chars()
                                .take(spin_string.len()-1).collect();
        spin_string = [spin_char, spin_string].concat();
    }
    return spin_string;
}

fn exchange(lineup: String, a_index: usize, b_index: usize) -> String {
    let mut exchange_chars: Vec<&str> = lineup.split("").collect();
    let a_char = exchange_chars[a_index+1];
    let b_char = exchange_chars[b_index+1];
    exchange_chars[a_index+1] = b_char;
    exchange_chars[b_index+1] = a_char;
    return exchange_chars.join("");
}

fn partner(lineup: String, a_program: &str, b_program: &str) -> String {
    let mut partner_chars: Vec<&str> = lineup.split("").collect();
    let a_index = partner_chars.iter().position(|&s| s == a_program).unwrap();
    let b_index = partner_chars.iter().position(|&s| s == b_program).unwrap();
    partner_chars[a_index] = b_program;
    partner_chars[b_index] = a_program;
    return partner_chars.join("");    
}

fn test_dance() {
    let lineup = "abcde".to_string();
    let moves = "s1,x3/4,pe/b".to_string();
    let result = dance(lineup, moves);
    let expected_result = "baedc".to_string();
    if result!=expected_result {
        println!("Test failed, got {}, expected {}", result, expected_result);
        std::process::exit(1);
    }
}