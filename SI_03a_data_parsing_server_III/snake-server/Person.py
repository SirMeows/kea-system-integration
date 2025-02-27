from pydantic import BaseModel


class Person(BaseModel):
    name: str
    hobbies: list[str] = []

    def __str__(self):
        return f"coffee.Person(name={self.name}, hobbies={self.hobbies})"
