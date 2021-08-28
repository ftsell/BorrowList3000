from django import template
from django.template import RequestContext

register = template.Library()


@register.simple_tag(takes_context=True)
def absolute_uri(context: RequestContext, relative_uri: str) -> str:
    return context.request.build_absolute_uri(relative_uri)
