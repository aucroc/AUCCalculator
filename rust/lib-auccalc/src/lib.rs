// Copyright © 2021 Alexander L. Hayes
// MIT License

use pyo3::prelude::*;

pub mod confusion;
use confusion::Confusion;

#[pyclass]
struct ConfusionPy {
    confusion: Confusion,
}

#[pymethods]
impl ConfusionPy {
    #[new]
    fn new(ypred: Vec<f64>, ytrue: Vec<i64>) -> PyResult<Self> {
        let confusion = Confusion::from_predictions(ypred, ytrue);
        Ok(Self { confusion })
    }

    #[args()]
    fn aucroc(&self) -> PyResult<f64> {
        Ok(self.confusion.calculate_aucroc())
    }

    #[args(min_recall="0.0")]
    fn aucpr(&self, min_recall: f64) -> PyResult<f64> {
        Ok(self.confusion.calculate_auc_pr(min_recall))
    }
}

#[pymodule]
fn auccalc(_py: Python, m: &PyModule) -> PyResult<()> {
    m.add_class::<ConfusionPy>()?;
    Ok(())
}
