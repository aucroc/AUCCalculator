// Copyright © 2021 Alexander L. Hayes
// MIT License

#[derive(Debug, PartialOrd, PartialEq)]
struct ClassSort {
    probability: f64,
    classification: i64,

    // TODO(hayesall): The `classification` was encoded as `int` in Java.
    //      It feels more natural for them to be booleans, but it feels
    //      unnatural to have them two different ways.
}

impl ClassSort {
    fn new(probability: f64, classification: i64) -> ClassSort {

        if probability > 1.0 || probability < 0.0 {
            panic!("Probabilities must be between 0.0 and 1.0.");
        }

        ClassSort { probability, classification }
    }
}


#[cfg(test)]
mod class_sort_tests {

    use super::*;

    #[test]
    fn test_initialize_classsort() {
        let cls = ClassSort::new(0.5, 1);
        assert_eq!(cls.probability, 0.5);
        assert_eq!(cls.classification, 1);
    }

    #[test]
    fn test_initialize_classsort0() {
        let cls = ClassSort::new(0.75, 0);
        assert_eq!(cls.probability, 0.75);
        assert_eq!(cls.classification, 0);
    }
}
