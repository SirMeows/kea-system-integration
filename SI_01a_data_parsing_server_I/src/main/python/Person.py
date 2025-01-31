class Person:
    def __init__(self, name=None, hobbies=None):
        self.name = name
        self.hobbies = hobbies if hobbies is not None else []

    def __str__(self):
        return f"Person(name={self.name}, hobbies={self.hobbies})"
