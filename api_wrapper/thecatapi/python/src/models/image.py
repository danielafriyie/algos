import typing

import model
import breed
import category


class Image(model.Model):
    def __init__(self,
                 id: typing.Optional[int] = None,
                 url: typing.Optional[str] = None,
                 width: typing.Optional[int] = None,
                 height: typing.Optional[int] = None,
                 mime_type: typing.Optional[str] = None,
                 breeds: typing.Optional[list[breed.Breed]] = None,
                 categories: typing.Optional[list[category.Category]] = None,
                 breed_ids: typing.Optional[str] = None,
                 **other_fields) -> None:
        super().__init__(id, **other_fields)
        self.url = url
        self.width = width
        self.height = height
        self.mime_type = mime_type
        self.breeds = breeds
        self.categories = categories
        self.breed_ids = breed_ids
