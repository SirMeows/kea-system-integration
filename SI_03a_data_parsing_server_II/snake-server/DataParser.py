import csv
import json
import xml.etree.ElementTree as ET
import yaml

from Person import Person


class DataParser:
    def parse_xml(self, file_path):
        tree = ET.parse(file_path)
        root = tree.getroot()

        person = Person(name=root.find('name').text, hobbies=[hobby.text for hobby in root.findall('hobbies')])
        print("Person object from XML:", person)
        return person

    def parse_json(self, file_path):
        with open(file_path, 'r') as file:
            person_dict = json.load(file)
            person = Person(**person_dict)
            print("Person object from JSON:", person)
            return person

    def parse_yaml(self, file_path):
        with open(file_path, 'r') as file:
            person_dict = yaml.safe_load(file)
            person = Person(**person_dict)
            print("Person object from YAML:", person)
            return person

    def parse_csv(self, file_path):
        with open(file_path, 'r') as file:
            reader = csv.DictReader(file)
            row = next(reader)
            person = Person(name=row['name'], hobbies=row['hobbies'].split(';'))
            print("Person object from CSV:", person)
            return person
