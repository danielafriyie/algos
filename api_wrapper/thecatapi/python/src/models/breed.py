import typing

from . import model


class Breed(model.Model):
    def __init__(self,
                 id: typing.Optional[int] = None,
                 name: typing.Optional[str] = None,
                 weight: typing.Optional[str] = None,
                 height: typing.Optional[str] = None,
                 life_span: typing.Optional[str] = None,
                 bred_for: typing.Optional[str] = None,
                 bred_group: typing.Optional[str] = None,
                 **other_fields) -> None:
        super().__init__(id, **other_fields)
        self.name = name
        self.weight = weight
        self.height = height
        self.life_span = life_span
        self.bred_for = bred_for
        self.bred_group = bred_group
