fn main() {
    test_dance()
}

fn dance(lineup: &str, moves: &str) -> String {
    
    return lineup.to_string();
}

fn test_dance() {
    let lineup = "abcde";
    let moves = "s1,x3/4,pe/b";
    let result = dance(lineup, moves);
    let expected_result = "baedc";
    if result!=expected_result {
        println!("Test failed, got {}, expected {}", result, expected_result);
        std::process::exit(1);
    }
}