import typing

from . import model
from . import breed
from . import category


class Image(model.Model):
    def __init__(self,
                 id: typing.Optional[int] = None,
                 url: typing.Optional[str] = None,
                 width: typing.Optional[int] = None,
                 height: typing.Optional[int] = None,
                 mime_type: typing.Optional[str] = None,
                 breeds: typing.Optional[list[dict]] = None,
                 categories: typing.Optional[list[dict]] = None,
                 breed_ids: typing.Optional[str] = None,
                 **other_fields) -> None:
        super().__init__(id, **other_fields)
        self.url = url
        self.width = width
        self.height = height
        self.mime_type = mime_type
        self.breeds = self.parse(breeds, breed.Breed)
        self.categories = self.parse(categories, category.Category)
        self.breed_ids = breed_ids

    @staticmethod
    def parse(data: list[dict], klass: type[model.Model]) -> list[model.Model]:
        if isinstance(data, dict):
            output = [klass(**data)]
        elif isinstance(data, list):
            output = [klass(**d) for d in data]
        else:
            output = []
        return output
