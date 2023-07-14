import typing

import model


class Category(model.Model):

    def __init__(self,
                 id: typing.Optional[int] = None,
                 name: typing.Optional[str] = None,
                 **other_fields) -> None:
        super().__init__(id, **other_fields)
        self.name = name
