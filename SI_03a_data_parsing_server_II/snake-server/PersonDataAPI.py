from fastapi import FastAPI

from DataParser import DataParser

app = FastAPI()


@app.get("/snake-person")
def get_person():
    parser = DataParser()
    return parser.parse_csv("../resources/me.csv")
